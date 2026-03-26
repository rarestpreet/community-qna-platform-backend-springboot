package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.user_request.UserProfileRequestDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserCommentResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserProfileResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserQuestionResponseDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.exception.UsernameAlreadyExistException;
import com.project.hearmeout_backend.mapper.CommentMapper;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.mapper.UserMapper;
import com.project.hearmeout_backend.model.Comment;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.enums.PostType;
import com.project.hearmeout_backend.repository.CommentRepository;
import com.project.hearmeout_backend.repository.PostRepository;
import com.project.hearmeout_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final CommentRepository commentRepo;

    public UserProfileResponseDTO getUserProfile(String username)
            throws UserNotFoundException {
        return UserMapper.toProfileDTO(checkAndGetUser(username));
    }

    public List<UserQuestionResponseDTO> getUserQuestions(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUser(username);
        List<Post> questions = postRepo.findByAuthor_IdAndPostType(currUser.getId(), PostType.QUESTION);

        return questions.stream()
                .map(PostMapper::toUserQuestionResponseDTO)
                .toList();
    }

    public User checkAndGetUser(Long userId)
            throws UserNotFoundException {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public User checkAndGetUser(String username)
            throws UserNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public List<UserAnswerResponseDTO> getUserAnswers(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUser(username);
        List<Post> answers = postRepo.findByAuthor_IdAndPostType(currUser.getId(), PostType.ANSWER);

        return answers.stream()
                .map(PostMapper::toUserAnswerResponseDTO)
                .toList();
    }

    public List<UserCommentResponseDTO> getUserComments(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUser(username);
        List<Comment> comments = commentRepo.findAllByAuthor_Id(currUser.getId());

        return comments.stream()
                .map(CommentMapper::toUserCommentResponseDto)
                .toList();
    }

    @Transactional
    public void updateUserDetails(String username, UserProfileRequestDTO userProfileRequestDTO)
            throws UserNotFoundException, UsernameAlreadyExistException, EmailAlreadyExistException {
        User currUser = checkAndGetUser(username);

        if (!Objects.equals(currUser.getUsername(), userProfileRequestDTO.getUsername()) &&
                userRepo.existsByUsername(userProfileRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistException("Username is already taken");
        }
        if (!currUser.getEmail().equals(userProfileRequestDTO.getEmail()) &&
                userRepo.existsByEmail(userProfileRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("Email is already registered");
        }

        currUser.setUsername(userProfileRequestDTO.getUsername());
        currUser.setEmail(userProfileRequestDTO.getEmail());
        currUser.setPassword(userProfileRequestDTO.getPassword());
        currUser.setEmailVerified(false);

        userRepo.save(currUser);
    }

    @Transactional
    public void terminateUserAccount(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUser(username);

        currUser.setAccountTerminated(true);
        currUser.setUsername("DELETED_"+currUser.getUsername());
        currUser.setEmail("DELETED_"+currUser.getEmail());
        currUser.setPassword("DELETED_"+currUser.getPassword());
        currUser.setEmailVerified(false);
        currUser.setReputation(0);

        userRepo.save(currUser);
    }

}
