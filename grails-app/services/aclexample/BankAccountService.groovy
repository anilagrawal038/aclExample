package aclexample

import com.aclExample.BankAccount
import com.aclExample.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.access.prepost.PreFilter

class BankAccountService {
    SpringSecurityService springSecurityService

    /*
    Note ::

    Within an expression, you can reference hasPermission(), authentication, principal, and, depending on the
    SecurityExpressionHandler in use, request. You can also reference any path variables defined in the
    @RequestMapping annotation:

    I found . ACL annotation not works with controller action. If found any solution, plz let me know the same.
    Logged JIRA issue : https://jira.grails.org/browse/GRAILS-11664

    Useful links:

    http://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
    http://blog.novoj.net/2012/03/27/combining-custom-annotations-for-securing-methods-with-spring-security/
    http://www.mscharhag.com/2013/10/grails-calling-bean-methods-in-spring.html


    Ex.
        @PreAuthorize("hasPermission(#bankAccountInstance.user,'ADMINISTRATION')")
        @PreAuthorize(" authentication.username == #bankAccountInstance.user.username ")
        @PreAuthorize(" principal.name == #bankAccountInstance.user.username ")
        @PreAuthorize("hasAnyRole([ROLE_ADMIN,ROLE_USER])")

     */

    def serviceMethod() {

    }

    @PostFilter("hasPermission(filterObject, 'READ') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'ADMINISTRATION')")
    List<BankAccount> listBankAccounts(Map params) {
        BankAccount.list(params)
    }

    @PreFilter("hasPermission(filterObject, 'READ') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'ADMINISTRATION')")
    List fetchBankAccountsBrief(List<BankAccount> bankAccounts) {
        List details = []
        bankAccounts.each { bankAccount ->
            details.add([bankAccount.accountNumber, bankAccount.accountBalance])
        }
        details
    }

    @Transactional
    @PreAuthorize("hasPermission(#bankAccount,'ADMINISTRATION')")
    BankAccount addAmountToAccount(BankAccount bankAccount, int balance) {
        bankAccount.setAccountBalance(bankAccount.accountBalance + balance)
        bankAccount.save(flush: true)
    }

    @PostAuthorize("hasPermission(returnObject,'READ') or hasPermission(returnObject,'ADMINISTRATION')")
    BankAccount searchAccount(String branch, String user) {
        BankAccount bankAccount = null
        if (branch) {
            if (user) {
                bankAccount = BankAccount.findByBranchAndUser(branch, User.findByUsername(user))
            } else {
                user = springSecurityService.authentication.principal.username
//                bankAccount = BankAccount.findByBranch(branch)
                bankAccount = BankAccount.findByBranchAndUser(branch, User.findByUsername(user))
            }
        } else {
            if (user) {
                bankAccount = BankAccount.findByUser(User.findByUsername(user))
            } else {
                //No criteria provided for search
            }
        }
        if (bankAccount == null) {
            throw new NullPointerException("No account found, matching provided criteria")
        }
        bankAccount
    }
}
