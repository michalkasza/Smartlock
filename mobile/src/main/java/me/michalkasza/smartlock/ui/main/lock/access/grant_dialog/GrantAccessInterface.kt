package me.michalkasza.smartlock.ui.main.lock.access.grant_dialog

import com.firebase.ui.auth.data.model.User
import me.michalkasza.smartlock.base.BaseView

interface GrantAccessInterface {
    interface View: BaseView {
    }
    interface UserInteractions {
        fun onEmailGrantClicked()
        fun onGrantConfirmationClicked()
    }
}