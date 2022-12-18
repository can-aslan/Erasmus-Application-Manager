package com.beam.beamBackend.service;

import java.util.HashSet;
import java.util.List;

import com.beam.beamBackend.model.RegisterStaff;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.UserLogin;
import com.beam.beamBackend.request.ChangePassword;
import com.beam.beamBackend.request.UserRequest;
import com.beam.beamBackend.response.RLoginUser;
import com.beam.beamBackend.response.RRefreshToken;
import com.beam.beamBackend.response.RRegisterStaff;

public interface IAccountService {
    RLoginUser login(UserLogin user) throws Exception;
    RRefreshToken refreshToken(String auth) throws Exception;
    boolean changePassword(String auth, ChangePassword passwords) throws Exception;
    User addUser(User user) throws Exception;
    RRegisterStaff addStaff(RegisterStaff staff) throws Exception;
    User addUserWithUserRequest(UserRequest userRequest) throws Exception;
    HashSet<User> addUserChunk(User[] users) throws Exception;
    List<User> getUsers();
}
