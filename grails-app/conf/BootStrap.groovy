import aclexample.BootstrapService

class BootStrap {
    BootstrapService bootstrapService

    def init = { servletContext ->
        bootstrapService.bootstrapUserRoles()
        bootstrapService.bootstrapAdmin()
        bootstrapService.bootstrapUsers()
        bootstrapService.bootstrapBankAccounts()
    }
    def destroy = {
    }
}
