package aclexample

import com.aclExample.BankAccount
import com.aclExample.Role
import com.aclExample.User
import com.aclExample.UserRole
import grails.plugin.springsecurity.acl.AclUtilService
import grails.transaction.Transactional
import org.springframework.context.MessageSource
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder as SCH

@Transactional
class BootstrapService {
    AclUtilService aclUtilService
    MessageSource messageSource

    def serviceMethod() {

    }

    void bootstrapUserRoles() {
        Role userRole = Role.findOrSaveByAuthority('ROLE_USER')
        Role adminRole = Role.findOrSaveByAuthority('ROLE_ADMIN')
    }

    void bootstrapAdmin() {
        String adminUsername = messageSource.getMessage("default.admin.username", []?.toArray(), Locale.getDefault())
        String adminPassword = messageSource.getMessage("default.admin.password", []?.toArray(), Locale.getDefault())
        User admin = User.findByUsername(adminUsername)
        if (!(admin)) {
            admin = new User(username: adminUsername, password: adminPassword).save()
            UserRole adminRole = new UserRole(user: admin, role: Role.findByAuthority('ROLE_ADMIN')).save()
        }
    }

    void bootstrapUsers() {
        for (index in 1..3) {
            User user = User.findByUsername('user' + index)
            if (!(user)) {
                user = new User(username: 'user' + index, password: 'password').save()
                UserRole userRole = new UserRole(user: user, role: Role.findByAuthority('ROLE_USER')).save()
            }
        }
    }

    void bootstrapBankAccounts() {
        loginAsAdmin()
        for (index in 1..3) {
            User user = User.findByUsername('user' + index)
            if (user) {
                BankAccount bankAccount = new BankAccount(user: user, accountBalance: 100, accountNumber: 1000 + index, branch: 'Noida').save()
                aclUtilService.addPermission(bankAccount, user.username, BasePermission.READ)
                aclUtilService.addPermission(bankAccount, user.username, BasePermission.WRITE)
                aclUtilService.addPermission(bankAccount, 'admin', BasePermission.ADMINISTRATION)
//                aclUtilService.addPermission(bankAccount, 'admin', BasePermission.WRITE)
            }
        }
        logoutAdmin()
    }

    private void loginAsAdmin() {
        String adminUsername = messageSource.getMessage("default.admin.username", []?.toArray(), Locale.getDefault())
        String adminPassword = messageSource.getMessage("default.admin.password", []?.toArray(), Locale.getDefault())
        SCH.context.authentication = new UsernamePasswordAuthenticationToken(adminUsername, adminPassword, AuthorityUtils.createAuthorityList('ROLE_ADMIN'))
    }

    private void logoutAdmin() {
        SCH.clearContext()
    }
}
