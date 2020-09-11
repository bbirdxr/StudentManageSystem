package cn.edu.seu.historycontest.service;

import cn.edu.seu.historycontest.security.UserPrincipal;

public interface AuthService {

    String login(String sid, String password, String code);
    void register(String sid, String cardId, String name);

}
