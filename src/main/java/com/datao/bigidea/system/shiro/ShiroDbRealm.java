package com.datao.bigidea.system.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;


@Named
public class ShiroDbRealm extends AuthorizingRealm {


    @Resource
    private EhCacheCacheManager cacheManager;

    /**
     * 验证权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {


        return null;
    }

    /**
     * 验证登录
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        return null;
    }
}
