class UrlMappings {

    static mappings = {
        "/login/$action?"(controller: "login")
        "/logout/$action?"(controller: "logout")
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')
    }
}
