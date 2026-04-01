package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.user_request.UserProfileRequestDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserCommentResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserProfileResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserQuestionResponseDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.InvalidOperationException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final CommentRepository commentRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserProfileResponseDTO getUserProfile(String username, Long userId)
            throws UserNotFoundException {
        return UserMapper.toProfileDTO(checkAndGetUserByUsername(username), userId);
    }

    public List<UserQuestionResponseDTO> getUserQuestions(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUserByUsername(username);
        List<Post> questions = postRepo.findByAuthor_IdAndPostType(currUser.getId(), PostType.QUESTION);

        return questions.stream()
                .map(PostMapper::toUserQuestionResponseDTO)
                .toList();
    }

    public User checkAndGetUserByUserId(Long userId)
            throws UserNotFoundException {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public User checkAndGetUserByUsername(String username)
            throws UserNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public User checkAndGetUserByEmail(String email)
            throws UserNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public List<UserAnswerResponseDTO> getUserAnswers(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUserByUsername(username);
        List<Post> answers = postRepo.findByAuthor_IdAndPostType(currUser.getId(), PostType.ANSWER);

        return answers.stream()
                .map(PostMapper::toUserAnswerResponseDTO)
                .toList();
    }

    public List<UserCommentResponseDTO> getUserComments(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUserByUsername(username);
        List<Comment> comments = commentRepo.findAllByAuthor_Id(currUser.getId());

        return comments.stream()
                .map(CommentMapper::toUserCommentResponseDto)
                .toList();
    }

    @Transactional
    public void updateUserDetails(String username, UserProfileRequestDTO userProfileRequestDTO, Long userId)
            throws UserNotFoundException, UsernameAlreadyExistException, EmailAlreadyExistException {
        User currUser = checkAndGetUserByUsername(username);

        if(!currUser.getId().equals(userId)){
            throw new InvalidOperationException("Operation only allowed for account owner");
        }

        if (!currUser.getUsername().equals(userProfileRequestDTO.getUsername()) &&
                userRepo.existsByUsername(userProfileRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistException("Username is already taken");
        }
        if (!currUser.getEmail().equals(userProfileRequestDTO.getEmail()) &&
                userRepo.existsByEmail(userProfileRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("Email is already registered");
        }

        currUser.setUsername(userProfileRequestDTO.getUsername());
        currUser.setEmail(userProfileRequestDTO.getEmail());
        currUser.setPassword(passwordEncoder.encode(userProfileRequestDTO.getPassword()));
        currUser.setEmailVerified(false);

        userRepo.save(currUser);
    }

    @Transactional
    public void terminateUserAccount(String username, Long userId)
            throws UserNotFoundException {
        User currUser = checkAndGetUserByUsername(username);

        if(!currUser.getId().equals(userId)){
            throw new InvalidOperationException("Operation only allowed for account owner");
        }

        currUser.setAccountTerminated(true);
        currUser.setUsername(currUser.getUsername());
        currUser.setEmail("DELETED"+currUser.getEmail());
        currUser.setPassword(currUser.getPassword());
        currUser.setEmailVerified(false);

        userRepo.save(currUser);
    }
}
