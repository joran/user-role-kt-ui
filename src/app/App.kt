package app

import app.users.listUsers
import app.users.newUser
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

data class Role(val id:String, val rolename:String, val description:String)
data class User(val userId:String, val name:String, val roles:Array<Role>)
enum class View(val id: String) {
    START("Users and roles"),
    USERS("Users"),
    ROLES("Roles"),
    NEW_USER("New user")
}

interface AppState : RState {
    var roles: Array<Role>
    var view: View

}

class App : RComponent<RProps, AppState>() {
    private fun handleUsersClick() = {
        setState {
            view = View.USERS
        }
    }
    private fun handleRolesClick() = {
        setState {
            view = View.ROLES
        }
    }
    private fun handleNewUserClick() = {
        setState {
            view = View.NEW_USER
        }
    }
    private fun handleOnSaveUser() = {user:User ->
        print("handleOnSaveUser" + user)
        // todo save user
        setState {
            view = View.USERS
        }
    }
    override fun AppState.init() {
        roles = arrayOf()
        view = View.START
    }

    override fun componentDidMount() {

    }

    override fun RBuilder.render() {
        navbar(state, handleUsersClick(), handleRolesClick(), handleNewUserClick())
        h1 { +state.view.id}
        when(state.view){
            View.USERS -> listUsers()
            View.NEW_USER -> newUser(handleOnSaveUser())
            View.ROLES -> rolesUI(state)
            View.START -> startUI(state)
        }
        footer {
            +"Built with Kotlin/JS and ReactJS"
            div("license"){
                +"Licensed under the "
                a("https://choosealicense.com/licenses/mit/"){+"MIT license"}
            }
        }
    }
}

private fun RBuilder.navbar(state: AppState, handleUsersClick: () -> Unit,
                            handleRolesClick: () -> Unit,
                            handleAddUserClick: () -> Unit) {
    div("navbar"){
        span("title"){+"User roles app"}
        a(href="#users") { +"Users"
            attrs.onClickFunction = { handleUsersClick() }
        }
        a(href="#roles"){+"Roles"
            attrs.onClickFunction = { handleRolesClick() }
        }
        span {
            a("#addUser"){
                +"New user"
                attrs.onClickFunction = { handleAddUserClick() }
            }
        }
    }
}

private fun RBuilder.startUI(state: AppState)  {
    div { +"Administration overview" }
}

fun RBuilder.rolesUI(state:AppState) {
    div { +"Roles UI goes here" }

}

fun RBuilder.app() = child(App::class) {}
