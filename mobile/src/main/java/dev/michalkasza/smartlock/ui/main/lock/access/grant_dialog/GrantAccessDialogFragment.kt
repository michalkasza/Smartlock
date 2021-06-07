package dev.michalkasza.smartlock.ui.main.lock.access.grant_dialog

import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.michalkasza.smartlock.R
import dev.michalkasza.smartlock.databinding.DialogGrantAccessBinding
import dev.michalkasza.smartlock.ui.components.ViewModelFactory
import dev.michalkasza.smartlock.ui.main.MainActivity

class GrantAccessDialogFragment: DialogFragment(), GrantAccessInterface.View {
    override val familiarName = "GrantAccess"
    private lateinit var grantAccessViewModel: GrantAccessViewModel
    private val mainActivity: MainActivity by lazy { activity as MainActivity }
    private var lockId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lockId = arguments!!.getString("lockId")
        val style = DialogFragment.STYLE_NORMAL
        val theme = R.style.AppTheme
        setStyle(style, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: DialogGrantAccessBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_grant_access,
                container,
                false)

        grantAccessViewModel = ViewModelProviders.of(this, ViewModelFactory(this, activity!!.application)).get(GrantAccessViewModel::class.java)
        viewBinding.viewModel = grantAccessViewModel

        grantAccessViewModel.lockId = lockId

        return viewBinding.root
    }

    override fun getSupportFragmentManager(): FragmentManager { return getSupportFragmentManager() }

    override fun getLinearVerticalLayoutManager(): LinearLayoutManager { return getLinearVerticalLayoutManager() }

    override fun showSnackbar(message: String) { }

    override fun showSnackbar(messageResourceId: Int) { }

    override fun setAppToolbarTitle(title: String?) { }

    override fun setAppToolbarTitle(titleResourceId: Int) { }

    companion object {
        fun newInstance(lockId: String?): GrantAccessDialogFragment {
            val f = GrantAccessDialogFragment()

            val args = Bundle()
            args.putString("lockId", lockId)
            f.arguments = args

            return f
        }
    }
}