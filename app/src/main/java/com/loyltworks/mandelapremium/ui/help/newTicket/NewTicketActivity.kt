package com.loyltworks.mandelapremium.ui.help.newTicket

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityNewTicketBinding
import com.loyltworks.mandelapremium.model.GetHelpTopicRetrieveRequest
import com.loyltworks.mandelapremium.model.HelpTopicRetrieveRequest
import com.loyltworks.mandelapremium.model.ObjHelpTopicList
import com.loyltworks.mandelapremium.model.SaveNewTicketQueryRequest
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity
import com.loyltworks.mandelapremium.ui.help.HelpViewModel
import com.loyltworks.mandelapremium.ui.help.adapter.HelptopicsAdapter
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.LoadingDialogue
import com.oneloyalty.goodpack.utils.BlockMultipleClick
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType

class NewTicketActivity : BaseActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityNewTicketBinding
    private lateinit var viewModel: HelpViewModel

    private var fileExtension: String = ""
    private var mProfileImagePath = ""
    private var selectedTopicPosition = 0

    private var helpTopicList = mutableListOf<ObjHelpTopicList>()

    override fun callInitialServices() {

    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        viewModel = ViewModelProvider(this)[HelpViewModel::class.java]

        binding.helpTopicSpinner.onItemSelectedListener = this
        binding.browseImage.setOnClickListener(this)
        binding.submit.setOnClickListener(this)
        binding.back.setOnClickListener(this)

        if (savedInstanceState != null) {
            selectedTopicPosition = savedInstanceState.getInt("selected_topic_position", 0)
        }

        observeViewModel()
        loadTopicsIfNeeded()
    }

    private fun loadTopicsIfNeeded() {
        if (viewModel.topicListLiveData.value == null) {
            LoadingDialogue.showDialog(this)
            viewModel.getHelpTopicListLiveData(
                HelpTopicRetrieveRequest(
                    GetHelpTopicRetrieveRequest(
                        "4",
                        PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserId.toString(),
                        "true"
                    )
                )
            )
        }
    }

    private fun observeViewModel() {

        viewModel.topicListLiveData.observe(this) {
            LoadingDialogue.dismissDialog()

            helpTopicList.clear()

            helpTopicList.add(
                ObjHelpTopicList(
                    HelpTopicId = -1,
                    HelpTopicName = "Select topic"
                )
            )

            it?.GetHelpTopicsResult?.objHelpTopicList?.let { list ->
                helpTopicList.addAll(list)
            }

            binding.helpTopicSpinner.adapter = HelptopicsAdapter(
                this,
                android.R.layout.simple_spinner_item,
                helpTopicList as ArrayList<ObjHelpTopicList>
            )

            // Restore selection safely
            binding.helpTopicSpinner.setSelection(selectedTopicPosition)
        }

        viewModel.saveNewTicketQueryLiveData.observe(this) {
            LoadingDialogue.dismissDialog()

            if (it != null && !it.ReturnMessage.isNullOrEmpty()) {
                val message = it.ReturnMessage
                val status = message.split("~")[0].toIntOrNull() ?: 0

                if (status > 0) {
                    Toast.makeText(this, "Support Ticket has been submitted successfully", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                } else {
                    Toast.makeText(this, "Failed to submit new ticket", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        if (BlockMultipleClick.click()) return

        when (v?.id) {

            R.id.back -> {
                onBackPressed()
            }

            R.id.browseImage -> {

                FileSelector.requiredFileTypes(FileType.IMAGES).open(this, object : FileSelectorCallBack {
                        override fun onResponse(fileSelectorData: FileSelectorData) {

                            mProfileImagePath = fileSelectorData.responseInBase64 ?: ""

                            fileExtension = fileSelectorData.extension ?: ""

                            binding.browseImg.visibility = View.VISIBLE
                            binding.browseImg.setImageBitmap(fileSelectorData.thumbnail)
                        }
                    })
            }

            R.id.submit -> {

                val selectedTopic =
                    helpTopicList.getOrNull(selectedTopicPosition)

                if (selectedTopic == null || selectedTopic.HelpTopicId == -1) {
                    Toast.makeText(
                        this,
                        "Select any topic.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                if (TextUtils.isEmpty(binding.queryDetails.text.toString())) {
                    binding.queryDetails.error = "Enter ticket details."
                    binding.queryDetails.requestFocus()
                    return
                }

                try {
                    LoadingDialogue.showDialog(this)

                    val user =
                        PreferenceHelper.getLoginDetails(this)?.UserList!![0]

                    viewModel.saveNewTicketQuery(
                        SaveNewTicketQueryRequest(
                            ActionType = "0",
                            ActorId = user.UserId.toString(),
                            CustomerName = user.Name,
                            Email = user.Email,
                            HelpTopic = selectedTopic.HelpTopicName,
                            HelpTopicID = selectedTopic.HelpTopicId.toString(),
                            IsQueryFromMobile = "true",
                            LoyaltyID = user.UserName,
                            Mobile = user.Mobile,
                            QueryDetails = binding.queryDetails.text.toString(),
                            QuerySummary = binding.queryDetails.text.toString(),
                            SourceType = "1",
                            ImageUrl = mProfileImagePath
                        )
                    )

                } catch (e: Exception) {
                    Log.d("TAG", "Submit Error: $e")
                }
            }
        }
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        if (parent?.id == R.id.helpTopicSpinner) {
            selectedTopicPosition = position
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected_topic_position", selectedTopicPosition)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_from_left,
            R.anim.slide_to_right
        )
    }
}
