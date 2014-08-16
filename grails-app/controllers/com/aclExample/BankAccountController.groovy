package com.aclExample

import aclexample.BankAccountService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.acl.AclUtilService
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.context.MessageSource
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.acls.domain.BasePermission

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class BankAccountController {

    MessageSource messageSource
    BankAccountService bankAccountService
    AclUtilService aclUtilService
    SpringSecurityService springSecurityService
    PermissionEvaluator permissionEvaluator

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    @Secured(['permitAll'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bankAccountService.listBankAccounts(params), model: [bankAccountInstanceCount: BankAccount.count()]
    }


    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def show(BankAccount bankAccountInstance) {
        System.out.println('Is logged in user have read rights on current account :' + permissionEvaluator.hasPermission(springSecurityService.authentication, bankAccountInstance, BasePermission.READ))
        respond bankAccountInstance
    }


    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def create() {
        respond new BankAccount(params)
    }


    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    @Transactional
    def save(BankAccount bankAccountInstance) {

        if (bankAccountInstance == null) {
            notFound()
            return
        }
        boolean isUserHavePermissionToCreateAccount = false
        //Security check
        if (springSecurityService.authentication.principal.username == bankAccountInstance.user.username
                || springSecurityService.authentication.authorities[0].toString() == "ROLE_ADMIN") {
            //permissionEvaluator.hasPermission(springSecurityService.authentication, bankAccountInstance, BasePermission.READ)
            isUserHavePermissionToCreateAccount = true
        }
        if (bankAccountInstance.hasErrors() || (!isUserHavePermissionToCreateAccount)) {
            flash.message = "You don't have sufficient permission to create account with provided user."
            respond bankAccountInstance.errors, view: 'create'
            return
        }

        bankAccountInstance.save flush: true
        aclUtilService.addPermission(bankAccountInstance, bankAccountInstance.user.username, BasePermission.READ)
        aclUtilService.addPermission(bankAccountInstance, bankAccountInstance.user.username, BasePermission.WRITE)
        String adminUsername = messageSource.getMessage("default.admin.username", []?.toArray(), Locale.getDefault())
        aclUtilService.addPermission(bankAccountInstance, adminUsername, BasePermission.ADMINISTRATION)
//        aclUtilService.addPermission(bankAccountInstance, adminUsername, BasePermission.WRITE)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bankAccountInstance.label', default: 'BankAccount'), bankAccountInstance.id])
                redirect bankAccountInstance
            }
            '*' { respond bankAccountInstance, [status: CREATED] }
        }
    }


    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def edit(BankAccount bankAccountInstance) {
        respond bankAccountInstance
    }

    @Transactional

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def update(BankAccount bankAccountInstance) {
        if (bankAccountInstance == null) {
            notFound()
            return
        }
        boolean isUserHavePermissionToUpdateAccount = false
        //Security check
        if ((permissionEvaluator.hasPermission(springSecurityService.authentication, bankAccountInstance, BasePermission.WRITE)
                || permissionEvaluator.hasPermission(springSecurityService.authentication, bankAccountInstance, BasePermission.ADMINISTRATION))
                && (springSecurityService.authentication.principal.username == bankAccountInstance.user.username
                || springSecurityService.authentication.authorities[0].toString() == "ROLE_ADMIN")) {
            //permissionEvaluator.hasPermission(springSecurityService.authentication, bankAccountInstance, BasePermission.READ)
            isUserHavePermissionToUpdateAccount = true
        }
        if (bankAccountInstance.hasErrors() || (!isUserHavePermissionToUpdateAccount)) {
            flash.message = "You don't have sufficient permission to update account with provided user."
            respond bankAccountInstance.errors, view: 'edit'
            return
        }

        bankAccountInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'BankAccount.label', default: 'BankAccount'), bankAccountInstance.id])
                redirect bankAccountInstance
            }
            '*' { respond bankAccountInstance, [status: OK] }
        }
    }

    @Transactional

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def delete(BankAccount bankAccountInstance) {

        if (bankAccountInstance == null) {
            notFound()
            return
        }
        boolean isUserHavePermissionToDeleteAccount = false
        //Security check
        if (permissionEvaluator.hasPermission(springSecurityService.authentication, bankAccountInstance, BasePermission.WRITE)
                || permissionEvaluator.hasPermission(springSecurityService.authentication, bankAccountInstance, BasePermission.ADMINISTRATION)) {
            //permissionEvaluator.hasPermission(springSecurityService.authentication, bankAccountInstance, BasePermission.READ)
            isUserHavePermissionToDeleteAccount = true
        }
        if (!isUserHavePermissionToDeleteAccount) {
            flash.message = "You don't have sufficient permission to delete account."
            redirect bankAccountInstance
            return
        }
        bankAccountInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'BankAccount.label', default: 'BankAccount'), bankAccountInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }


    @Secured(['permitAll'])
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bankAccountInstance.label', default: 'BankAccount'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }


    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def fetchAccountsBrief() {
        List details = bankAccountService.fetchBankAccountsBrief(BankAccount.list())
        render(view: 'showAccountsBrief', model: [accountsDetails: details])
    }


    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def addAmountForm() {
        BankAccount bankAccount = BankAccount.findById(params.id)
        render(view: 'addAmountForm', model: [bankAccountInstance: bankAccount])
    }

    @Transactional

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def addAmount() {
        BankAccount bankAccount = BankAccount.findById(params.id)
        try {
            bankAccount = bankAccountService.addAmountToAccount(bankAccount, Integer.parseInt(params.balance))
        } catch (AccessDeniedException e) {
            println 'exception in controller ' + e
            flash.message = 'You do not have sufficient privileges to add balance in this account'
            render(view: "addAmountForm", model: [id: params.id, bankAccountInstance: bankAccount])
            return
        }
        render(view: 'showAccountsBrief', model: [accountsDetails: [[bankAccount.accountNumber, bankAccount.accountBalance]]])
    }

    @Secured(['permitAll'])
    def searchAccountForm() {
        render view: 'searchAccountForm'
    }

    @Secured(['permitAll'])
    def searchAccount() {
        BankAccount bankAccountInstance
        try {
            bankAccountInstance = bankAccountService.searchAccount(params.branch, params.user)
        } catch (NullPointerException e1) {
            println 'exception in controller for action searchAccount :' + e1
            flash.message = 'No account found, matching provided criteria'
            render view: 'searchAccountForm'
            return
        } catch (AccessDeniedException e2) {
            println 'exception in controller for action searchAccount :' + e2
            flash.message = "You don't have read rights for account found, matching provided criteria"
            render view: 'searchAccountForm'
            return
        }
        redirect bankAccountInstance
    }
}
