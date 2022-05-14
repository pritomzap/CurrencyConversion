package com.currencyconversion.app.ui.customViews

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.currencyconversion.app.R
import com.currencyconversion.app.data.models.common.CommonDialogBuilder
import com.currencyconversion.app.databinding.LayoutCommonDialogBinding
import com.currencyconversion.app.service.utils.gone
import com.currencyconversion.app.ui.viewModels.DialogFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class CommonDialogFragment : AppCompatDialogFragment() {

    private lateinit var binding: LayoutCommonDialogBinding
    private val mainViewModels by activityViewModels<DialogFragmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_common_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModels.fetchData().observe(viewLifecycleOwner, {
            initDialog(it)
        }
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
    private fun initDialog(dialogInitialValue: CommonDialogBuilder) {
        binding.apply {
            dialogTitle.text = dialogInitialValue.titleText
                ?: requireContext().getString(R.string.alert_dialog_common_error_title)
            dialogTitle.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimaryDark
                )
            )
            dialogSubtitle.text = dialogInitialValue.subTitle
            dialogSubtitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
            if (dialogInitialValue.titleImage != null) {
                dialogImage.setImageDrawable(dialogInitialValue.titleImage)
            }else{
                dialogImage.setImageResource(if (dialogInitialValue.type == CommonDialogBuilder.CommonDialogType.ERROR) R.drawable.ic_failed_icon else R.drawable.ic_success_icon)
            }
            dialogConfirmBtn.text = if (dialogInitialValue.buttonText.isNullOrEmpty()) requireContext().getString(R.string.app_common_done) else dialogInitialValue.buttonText
            dialogConfirmBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.drawable_active_btn_background)

            dialogConfirmBtn.setOnClickListener {
                if (dialogInitialValue.listener != null){
                    dialogInitialValue.listener!!.invoke()
                }
                dismiss()
            }
            if (!dialogInitialValue.closeIconVisibility!!) binding.dialogCloseIcon.gone()
            binding.dialogCloseIcon.setOnClickListener {
                if (dialogInitialValue.closeIconClickListener != null){
                    dialogInitialValue.closeIconClickListener!!.invoke()
                }
                dismiss()
            }
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            this.dismiss()
        }catch (e:Exception){}
        super.show(manager, tag)
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }
}