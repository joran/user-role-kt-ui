package app.users

import app.User
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*

interface EditUserState : RState {
    var userId: String
    var name: String
}

interface EditUserProps : RProps {
    var user: User
    var onSaveUser: (User) -> Unit
}

class EditUser(props: EditUserProps) : RComponent<EditUserProps, EditUserState>(props) {
    override fun EditUserState.init(props: EditUserProps){
        userId = props.user.userId
        name = props.user.userId
    }

    private fun onUsernameChanged() = { event: Event ->
        val target = event.target as HTMLInputElement
        setState{
            userId = target.value
        }
    }
    private fun onNameChanged() = { event: Event ->
        val target = event.target as HTMLInputElement
        setState{
            name = target.value
        }
    }
    override fun RBuilder.render() {
        div {
            form{
                div{
                    label { +"Username: " }
                    input(type = InputType.text) {
                        attrs {
                            placeholder = "Username"
                            value = state.userId
                            onChangeFunction = onUsernameChanged()
                        }
                    }
                }
                div {
                    label { +"Full name: " }
                    input(type = InputType.text) {
                        attrs {
                            placeholder = "Full name"
                            value = state.name
                            onChangeFunction = onNameChanged()
                        }
                    }
                }
                div {
                    label { +"Roles: " }
                    input(type = InputType.text) {
                    }
                }
                input (type = InputType.submit){  }
            }
        }
    }

}

fun RBuilder.editUser(user: User, handleOnSaveUser: (User) -> Unit) =
        child(EditUser::class) {
            attrs.user = user
            attrs.onSaveUser = handleOnSaveUser
        }
fun RBuilder.newUser(handleOnSaveUser: (User) -> Unit) =
        child(EditUser::class) {
            attrs.user = User("", "", arrayOf())
            attrs.onSaveUser = handleOnSaveUser
        }