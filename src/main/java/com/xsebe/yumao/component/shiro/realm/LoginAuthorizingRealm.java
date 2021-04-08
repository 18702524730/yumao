package com.xsebe.yumao.component.shiro.realm;

import com.xsebe.yumao.exception.ShiroAccountNotFoundException;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Perm;
import com.xsebe.yumao.model.Role;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.service.PermService;
import com.xsebe.yumao.service.RoleService;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.utility.PasswordCipherUtility;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Component("loginAuthorizingRealm")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/")
public final class LoginAuthorizingRealm extends AuthorizingRealm {

    public LoginAuthorizingRealm() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(final AuthenticationToken token, final AuthenticationInfo info) {
                UsernamePasswordToken inputToken = (UsernamePasswordToken) token;
                String[] tmp = inputToken.getUsername().split("/");
                int userType = Integer.parseInt(tmp[0]);
                String inputAccount = tmp[1];
                char[] inputLoginPwd = inputToken.getPassword();

                SimpleAuthenticationInfo persistentInfo = (SimpleAuthenticationInfo) info;
                User persistentUser = (User) persistentInfo.getPrincipals().getPrimaryPrincipal();
                char[] persistentLoginPwdCipher = (char[]) persistentInfo.getCredentials();
                byte[] persistentLoginPwdSalt = persistentInfo.getCredentialsSalt().getBytes();

                if (User.USER_TYPE_PERSONAL == userType) {
                    // 个人登录情况，判断用户名/手机号码/邮箱和登录密码是否一致
                    if (!((persistentUser.getUsername().equalsIgnoreCase(inputAccount) 
                            || persistentUser.getMobile().equalsIgnoreCase(inputAccount) || persistentUser
                            .getEmail().equalsIgnoreCase(inputAccount)) && Arrays.equals(Base64.decodeBase64(new String(persistentLoginPwdCipher)),
                            PasswordCipherUtility.getLoginPasswordCipherBySalt(new String(inputLoginPwd), persistentLoginPwdSalt)))) {
                        return false;
                    }
                } else {
                    // 企业登录情况，判断用户名/统一社会信用代码或组织机构代码和登录密码是否一致
                    if (!((persistentUser.getUsername().equalsIgnoreCase(inputAccount) 
                            || persistentUser.getEntUniCreditCode().equalsIgnoreCase(inputAccount)) && Arrays
                            .equals(Base64.decodeBase64(new String(persistentLoginPwdCipher)),
                                    PasswordCipherUtility.getLoginPasswordCipherBySalt(new String(inputLoginPwd), persistentLoginPwdSalt)))) {
                        return false;
                    }
                }

                return true;
            }
        };
        setCredentialsMatcher(credentialsMatcher);
    }

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermService permService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        SimpleAuthorizationInfo persistentInfo = new SimpleAuthorizationInfo();

        User user = (User) principals.getPrimaryPrincipal();

        List<Role> roleList;
        try {
            roleList = roleService.getsByUserId(user.getId());
        } catch (YumaoException ex) {
            throw new AuthorizationException("列出角色信息时出错, " + ex.getHappenMessage());
        }
        List<Perm> permList;
        for (Role role : roleList) {
            persistentInfo.addRole(role.getLabel());

            try {
                permList = permService.getsByRoleId(role.getId());
            } catch (YumaoException ex) {
                throw new AuthorizationException("列出权限信息时出错, " + ex.getHappenMessage());
            }
            for (Perm perm : permList) {
                persistentInfo.addStringPermission(perm.getLabel());
            }
        }

        return persistentInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) {
        UsernamePasswordToken inputToken = (UsernamePasswordToken) token;
        String[] tmp = inputToken.getUsername().split("/");
        int userType = Integer.parseInt(tmp[0]);
        String inputAccount = tmp[1];

        User user;
        if (User.USER_TYPE_PERSONAL == userType) {
            try {
                user = userService.getByUsernameOrMobileOrEmail(inputAccount, inputAccount, inputAccount);
            } catch (YumaoException ex) {
                throw new AccountException("查询账户信息时出错, " + ex.getHappenMessage());
            }
        } else {
            try {
                user = userService.getByUsernameOrEntUniCreditCode(inputAccount, inputAccount);
            } catch (YumaoException ex) {
                throw new AccountException("查询账户信息时出错, " + ex.getHappenMessage());
            }
        }

        if (user == null) {
            throw new ShiroAccountNotFoundException(inputAccount);
        }

        Object persistentUser = user;// 通过 inputToken 的信息从持久层查询用户信息
        char[] persistentLoginPwdCipher = user.getLoginPasswordCipher().toCharArray();
        byte[] persistentLoginPwdSalt = Base64.decodeBase64(user.getLoginPasswordSalt());

        SimpleAuthenticationInfo persistentInfo = new SimpleAuthenticationInfo();
        persistentInfo.setPrincipals(new SimplePrincipalCollection(persistentUser, "realm1"));
        persistentInfo.setCredentials(persistentLoginPwdCipher);
        persistentInfo.setCredentialsSalt(new SimpleByteSource(persistentLoginPwdSalt));
        return persistentInfo;
    }




}
