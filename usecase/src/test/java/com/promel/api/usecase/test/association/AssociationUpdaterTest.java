package com.promel.api.usecase.test.association;

import com.promel.api.domain.model.Association;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.usecase.association.AssociationFinder;
import com.promel.api.usecase.association.AssociationUpdater;
import com.promel.api.usecase.association.adapter.AssociationAdapter;
import com.promel.api.usecase.exception.CustomForbiddenException;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.user.UserAccountFinder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssociationUpdaterTest {
    @InjectMocks
    private AssociationUpdater associationUpdater;
    @Mock
    private AssociationAdapter adapter;
    @Mock
    private AssociationFinder associationFinder;
    @Mock
    private UserAccountFinder userAccountFinder;

    @Test
    @DisplayName("update invite code when successful should change data")
    public void updateInviteCodeWhenSuccessfulShouldChangeData() {
        when(userAccountFinder.findByEmail(anyString())).thenReturn(buildUserAccount());
        when(associationFinder.findById(anyLong())).thenReturn(buildAssociation());
        when(adapter.save(any(Association.class))).then(ans -> ans.getArgument(0));

        var association = associationUpdater.updateInviteCode("TestInviteCode", 1L, "userAdmin@email.com");

        assertNotNull(association);
        assertEquals("TestInviteCode", association.getInviteCode());
    }

    @Test
    @DisplayName("update invite code invite when code already exists should throw ResourceConflictException")
    public void updateInviteCodeWhenAlreadyExistsShouldThrowResourceConflictException() {
        when(userAccountFinder.findByEmail(anyString())).thenReturn(buildUserAccount());
        when(associationFinder.findById(anyLong())).thenReturn(buildAssociation());
        when(associationFinder.associationExistsByInviteCode(anyString())).thenReturn(true);
        assertThrows(ResourceConflictException.class, () -> associationUpdater.updateInviteCode("TestInviteCode", 1L, "userAdmin@email.com"));
    }

    @Test
    @DisplayName("update invite code when user is not own should throw CustomForbiddenException")
    public void updateInviteCodeWhenUserIsNotOwnShouldThrowCustomForbiddenException() {
        when(userAccountFinder.findByEmail(anyString())).thenReturn(buildUserAccount());
        assertThrows(CustomForbiddenException.class, () -> associationUpdater.updateInviteCode("TestInviteCode", 0L, "userAdmin@email.com"));
    }

    private UserAccount buildUserAccount() {
        var user = new UserAccount();
        user.setAssociation(buildAssociation());
        return user;
    }

    private Association buildAssociation() {
        var association = new Association();
        association.setId(1L);
        return association;
    }
}
