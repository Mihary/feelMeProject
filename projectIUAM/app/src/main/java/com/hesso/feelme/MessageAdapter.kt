package com.hesso.feelme

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.bot_message.view.*
import kotlinx.android.synthetic.main.user_message.view.*
import java.text.SimpleDateFormat
import java.util.*


private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_BOT_MESSAGE = 2

object DateUtils {
    fun fromMillisToTimeString(millis: Long) : String {
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(millis)
    }
}

class MessageAdapter (private val context: Context, messages: ArrayList<Any>):RecyclerView.Adapter<MessageViewHolder>(){





    private val messages: ArrayList<Message> = ArrayList()

    fun addMessage(message: Message){
        messages.add(message)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return messages.size //To change body of created functions use File | Settings | File Templates.
    }


    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)

        return if( message.user=="moi" ) {
            VIEW_TYPE_MY_MESSAGE
        }
        else {
            VIEW_TYPE_BOT_MESSAGE
        }
    }

    override fun onBindViewHolder(p0: MessageViewHolder, p1: Int) {
        val message = messages.get(p1)

        p0?.bind(message) //To change body of created functions use File | Settings | File Templates.
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.user_message, parent, false))
        } else {
            BotMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.bot_message, parent, false))
        }

    }





    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtMyMessage
        private var timeText: TextView = view.txtMyMessageTime

        override fun bind(message: Message) {
            messageText.text = message.message
            timeText.text = DateUtils.fromMillisToTimeString(message.time)

        }


    }

    inner class BotMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtBotMessage
        private var userText: TextView = view.txtBotUser
        private var timeText: TextView = view.txtBotMessageTime

        override fun bind(message: Message) {
            messageText.text = message.message
            userText.text = message.user
            timeText.text = DateUtils.fromMillisToTimeString(message.time)

        }


    }



}
open class MessageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    open fun bind(message: Message){}

}