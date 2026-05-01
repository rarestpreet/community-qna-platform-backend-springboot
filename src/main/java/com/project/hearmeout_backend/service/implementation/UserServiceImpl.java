package com.project.hearmeout_backend.service.implementation;

import com.project.hearmeout_backend.dto.request.user_request.UserProfileModificationRequestDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserCommentResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserProfileResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserQuestionResponseDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.InvalidOperationException;
import com.project.hearmeout_backend.exception.UserAlreadyExistException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.enums.PostType;
import com.project.hearmeout_backend.repository.CommentRepository;
import com.project.hearmeout_backend.repository.PostRepository;
import com.project.hearmeout_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final CommentRepository commentRepo;

    public UserProfileResponseDTO getUserProfile(String username, Long currUserId)
            throws UserNotFoundException {
        UserProfileResponseDTO profileDTO = userRepo.getUserProfileByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        profileDTO.setOperable(profileDTO.getUserId().equals(currUserId));

        return profileDTO;
    }

    @Transactional(readOnly = true)
    public List<UserQuestionResponseDTO> getUserQuestions(String username)
            throws UserNotFoundException {
        return postRepo.findUserQuestionByUsername(username, PostType.QUESTION);
    }

    @Transactional(readOnly = true)
    public User checkAndGetUserByUserId(Long userId)
            throws UserNotFoundException {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Transactional(readOnly = true)
    public User checkAndGetUserByUsername(String username)
            throws UserNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Transactional(readOnly = true)
    public User checkAndGetUserByEmail(String email)
            throws UserNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Transactional(readOnly = true)
    public List<UserAnswerResponseDTO> getUserAnswers(String username)
            throws UserNotFoundException {
        return postRepo.findUserAnswerByUsername(username, PostType.ANSWER);
    }

    @Transactional(readOnly = true)
    public List<UserCommentResponseDTO> getUserComments(String username)
            throws UserNotFoundException {
        return commentRepo.findUserCommentsByUsername(username);
    }

    @Transactional
    public void updateUserDetails(UserProfileModificationRequestDTO requestDTO, Long currUserId)
            throws UserNotFoundException, UserAlreadyExistException, EmailAlreadyExistException {
        if (currUserId == null || currUserId.equals(requestDTO.getUserId())) {
            throw new InvalidOperationException("You are not allowed to perform this operation (user update)");
        }

        User currUser = checkAndGetUserByUserId(currUserId);

        if (!currUser.getUsername().equals(requestDTO.getUsername()) &&
                !currUser.getEmail().equals(requestDTO.getEmail()) &&
                userRepo.existsByUsernameOrEmail(requestDTO.getUsername(), requestDTO.getEmail())) {
            throw new UserAlreadyExistException("Username is already taken");
        }

        currUser.setUsername(requestDTO.getUsername());
        currUser.setEmail(requestDTO.getEmail());
        currUser.setAccountVerified(false);

        userRepo.save(currUser);
    }

    @Transactional
    public void terminateUserAccount(Long userId)
            throws UserNotFoundException {
        User currUser = checkAndGetUserByUserId(userId);


        /*
        update logic for account delete

        currUser.setAccountTerminated(true);
        currUser.setEmail("DELETED" + currUser.getEmail());
        currUser.setAccountVerified(false);
        */

        userRepo.delete(currUser);
    }
}
