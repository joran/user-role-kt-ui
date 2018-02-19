package app.users

import app.User
import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.*

interface ListUsersState: RState{
    var users: Array<User>
}

interface ListUsersProps : RProps {

}

class ListUsers : RComponent<ListUsersProps, ListUsersState>() {
    override fun ListUsersState.init(){
        users = arrayOf()
    }
    override fun componentDidMount() {
        loadUsers()
    }

    fun loadUsers():Unit{
        val url = "http://localhost:8080/api/user"
        val req = XMLHttpRequest()
        req.onloadend = fun(event: Event) {
            this.setState{
                users = JSON.parse(req.responseText)
            }
        }
        req.open("GET", url, true)
        req.send()
    }

    override fun RBuilder.render() {
        table {
            thead {
                tr {
                    th { +"Uid" }
                    th { +"Name" }
                    th { +"Roles" }
                }
            }
            tbody {
                for (user in state.users) {
                    tr {
                        td { +user.userId }
                        td { +user.name }
                        td {
                            for (role in user.roles) {
                                +role.rolename
                            }
                        }
                    }
                }
            }
        }
    }

}

fun RBuilder.listUsers() = child(ListUsers::class) {  }