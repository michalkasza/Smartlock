package dev.michalkasza.smartlock.ui.main.lock.access.grant_dialog

import dev.michalkasza.smartlock.base.BaseView

interface GrantAccessInterface {
    interface View: BaseView {
    }
    interface UserInteractions {
        fun onEmailGrantClicked()
        fun onGrantConfirmationClicked()
    }
}