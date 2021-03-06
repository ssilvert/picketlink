/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.picketlink.idm;

import org.picketlink.idm.config.IdentityConfiguration;
import org.picketlink.idm.credential.Credential;
import org.picketlink.idm.model.Group;
import org.picketlink.idm.model.IdentityType;
import org.picketlink.idm.model.Realm;
import org.picketlink.idm.model.Role;
import org.picketlink.idm.model.Tier;
import org.picketlink.idm.model.User;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.spi.IdentityStoreInvocationContextFactory;
import org.picketlink.idm.spi.StoreFactory;

/**
 * Manages all Identity Management related operations.
 * 
 * @author Shane Bryzak
 */
public interface IdentityManager {

    /**
     * This method must be invoked to set up the IdentityManager instance before any identity management operations may be
     * performed.
     * 
     * @param configuration
     */
    void bootstrap(IdentityConfiguration configuration, IdentityStoreInvocationContextFactory contextFactory);

    /**
     * Sets the IdentityStoreFactory implementation to be used to create IdentityStore instances
     * 
     * @param factory
     */
    void setIdentityStoreFactory(StoreFactory factory);

    // General

    void add(IdentityType identityType);

    void update(IdentityType identityType);

    void remove(IdentityType identityType);

    // User

    User getUser(String name);

    // Group

    Group getGroup(String groupId);

    Group getGroup(String groupName, Group parent);

    boolean isMember(IdentityType identityType, Group group);

    void addToGroup(IdentityType identityType, Group group);

    void removeFromGroup(IdentityType identityType, Group group);

    // Roles

    Role getRole(String name);

    boolean hasGroupRole(IdentityType identityType, Role role, Group group);

    void grantGroupRole(IdentityType identityType, Role role, Group group);

    void revokeGroupRole(IdentityType identityType, Role role, Group group);

    boolean hasRole(IdentityType identityType, Role role);

    void grantRole(IdentityType identityType, Role role);

    void revokeRole(IdentityType identityType, Role role);

    // Query API

    <T extends IdentityType> IdentityQuery<T> createQuery();

    // Credential management

    boolean validateCredential(User user, Credential credential);

    void updateCredential(User user, Credential credential);

    // User / Role / Group enablement / expiry

    IdentityType lookupIdentityByKey(String key);

    // Attributes


    void loadAttribute(IdentityType identityType, String attributeName);

    // Realm

    void createRealm(Realm realm);

    void removeRealm(Realm realm);

    Realm getRealm(String name);

    // Tier

    void createTier(Tier tier);

    void removeTier(Tier tier);

    Tier getTier(String id);

    // Context

    IdentityManager forRealm(Realm realm);

    IdentityManager forTier(Tier tier);

    /**
     * <p>
     * Updates the given {@link Role} instance.
     * </p>
     * <p><b>Important:</b> Only the role attributes will be updated, it is not possible to update the role
     * name. For that you should recreate the role with the new name.</p>
     * 
     * @param role
     */
    void updateRole(Role role);

    /**
     * <p>
     * Updates the given {@link Group} instance.
     * </p>
     * <p><b>Important:</b> Only the group attributes will be updated, it is not possible to update the group
     * name or parent group. For that you should recreate the group.</p>
     * 
     * @param group
     */
    void updateGroup(Group group);
}
