package com.example.testing

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.testing.MainActivity.Companion.ANONYMOUS
import com.example.testing.databinding.ImageMessageBinding
import com.example.testing.databinding.MessageBinding
import com.example.testing.model.FriendlyMessage

class SimpleFriendlyMessageAdapter(
    private val messages: MutableList<FriendlyMessage> = mutableListOf(),
    private val currentUserName: String?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_TEXT) {
            val view = inflater.inflate(R.layout.message, parent, false)
            val binding = MessageBinding.bind(view)
            MessageViewHolder(binding)
        } else {
            val view = inflater.inflate(R.layout.image_message, parent, false)
            val binding = ImageMessageBinding.bind(view)
            ImageMessageViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (message.imageUrl.isNullOrBlank()) {
            (holder as MessageViewHolder).bind(message)
        } else {
            (holder as ImageMessageViewHolder).bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].imageUrl.isNullOrBlank()) VIEW_TYPE_TEXT else VIEW_TYPE_IMAGE
    }

    inner class MessageViewHolder(private val binding: MessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FriendlyMessage) {
            binding.messageTextView.text = item.text
            setTextColor(item.name, binding.messageTextView)

            binding.messengerTextView.text = item.name ?: ANONYMOUS
            if (null == item.photoUrl) {
                binding.messengerImageView.setImageResource(R.drawable.ic_account_circle_black_36dp)
            } else {
                loadImageIntoView(binding.messengerImageView, item.photoUrl)
            }
        }

        private fun setTextColor(userName: String?, textView: TextView) {
            if (userName != ANONYMOUS && currentUserName == userName && userName != null) {
                textView.setBackgroundResource(R.drawable.rounded_message_blue)
                textView.setTextColor(Color.WHITE)
            } else {
                textView.setBackgroundResource(R.drawable.rounded_message_gray)
                textView.setTextColor(Color.BLACK)
            }
        }
    }

    inner class ImageMessageViewHolder(private val binding: ImageMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FriendlyMessage) {
            item.imageUrl?.let {
                loadImageIntoView(binding.messageImageView, item.imageUrl, false)
            }
        }
    }

    private fun loadImageIntoView(view: ImageView, url: String, isCircular: Boolean = true) {
        if (url.startsWith("gs://")) {
            // TODO:
            /*val storageReference = Firebase.storage.getReferenceFromUrl(url)
            storageReference.downloadUrl
                .addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    loadWithGlide(view, downloadUrl, isCircular)
                }
                .addOnFailureListener { e ->
                    Log.w(
                        TAG,
                        "Getting download url was not successful.",
                        e
                    )
                }*/
        } else {
            loadWithGlide(view, url, isCircular)
        }
    }

    private fun loadWithGlide(view: ImageView, url: String, isCircular: Boolean = true) {
        Glide.with(view.context).load(url).into(view)
        var requestBuilder = Glide.with(view.context).load(url)
        if (isCircular) {
            requestBuilder = requestBuilder.transform(CircleCrop())
        }
        requestBuilder.into(view)
    }

    fun updateData(messages: MutableList<FriendlyMessage>) {
        this.messages.clear()
        this.messages.addAll(messages)
        notifyDataSetChanged()
    }

    companion object {
        const val TAG = "MessageAdapter"
        const val VIEW_TYPE_TEXT = 1
        const val VIEW_TYPE_IMAGE = 2
    }
}
