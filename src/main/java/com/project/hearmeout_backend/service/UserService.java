package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.SecurityRequest.RegisterRequestDTO;
import com.project.hearmeout_backend.dto.request.UserRequest.UserProfileRequestDTO;
import com.project.hearmeout_backend.dto.response.UserResponse.UserAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.UserResponse.UserCommentResponseDTO;
import com.project.hearmeout_backend.dto.response.UserResponse.UserProfileResponseDTO;
import com.project.hearmeout_backend.dto.response.UserResponse.UserQuestionResponseDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.exception.UsernameAlreadyExistException;
import com.project.hearmeout_backend.mapper.AnswerMapper;
import com.project.hearmeout_backend.mapper.CommentMapper;
import com.project.hearmeout_backend.mapper.QuestionMapper;
import com.project.hearmeout_backend.mapper.UserMapper;
import com.project.hearmeout_backend.model.Comment;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.enums.PostType;
import com.project.hearmeout_backend.repository.CommentRepository;
import com.project.hearmeout_backend.repository.PostRepository;
import com.project.hearmeout_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .map(QuestionMapper::toQuestionDTO)
                .toList();
    }

    private User checkAndGetUser(String username) throws UserNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with username: " + username)
                );
    }

    public List<UserAnswerResponseDTO> getUserAnswers(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUser(username);
        List<Post> answers = postRepo.findByAuthor_IdAndPostType(currUser.getId(), PostType.ANSWER);

        return answers.stream()
                .map(AnswerMapper::toAnswerDTO)
                .toList();
    }

    public List<UserCommentResponseDTO> getUserComments(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUser(username);
        List<Comment> comments = commentRepo.findAllByAuthor_Id(currUser.getId());

        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .toList();
    }

    public void updateUserDetails(String
                                          username, UserProfileRequestDTO userProfileRequestDTO)
            throws UserNotFoundException, UsernameAlreadyExistException, EmailAlreadyExistException {
        if (userRepo.existsByUsername(userProfileRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistException("Username is already taken");
        }
        if (userRepo.existsByEmail(userProfileRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("Email is already registered");
        }

        User currUser = checkAndGetUser(username);

        currUser.setUsername(userProfileRequestDTO.getUsername());
        currUser.setEmail(userProfileRequestDTO.getEmail());
        currUser.setPassword(userProfileRequestDTO.getPassword());
        currUser.setEmailVerified(false);

        userRepo.save(currUser);
    }

    public void terminateUserAccount(String username)
            throws UserNotFoundException {
        User currUser = checkAndGetUser(username);

        currUser.setAccountTerminated(true);
        currUser.setUsername(null);
        currUser.setEmail(null);
        currUser.setPassword(null);
        currUser.setEmailVerified(false);
        currUser.setReputation(0);

        userRepo.save(currUser);
    }
}
