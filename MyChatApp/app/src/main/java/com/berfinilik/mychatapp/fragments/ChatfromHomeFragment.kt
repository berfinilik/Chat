package com.berfinilik.mychatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.berfinilik.mychatapp.R
import com.berfinilik.mychatapp.Utils
import com.berfinilik.mychatapp.adapter.MessageAdapter
import com.berfinilik.mychatapp.databinding.FragmentChatfromHomeBinding
import com.berfinilik.mychatapp.modal.Messages
import com.berfinilik.mychatapp.mvvm.ChatAppViewModel
import de.hdodenhof.circleimageview.CircleImageView

class ChatfromHome : Fragment() {

    lateinit var args : ChatfromHomeFragmentArgs
    lateinit var binding: FragmentChatfromHomeBinding
    lateinit var viewModel : ChatAppViewModel
    lateinit var toolbar: Toolbar
    lateinit var adapter : MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chatfrom_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolBarChat)
        val circleImageView = toolbar.findViewById<CircleImageView>(R.id.chatImageViewUser)
        val textViewName = toolbar.findViewById<TextView>(R.id.chatUserName)

        args = ChatfromHomeFragmentArgs.fromBundle(requireArguments())
        viewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        Glide.with(view.getContext()).load(args.recentchats.friendsimage!!).placeholder(R.drawable.person).dontAnimate().into(circleImageView);
        textViewName.setText(args.recentchats.name)

        binding.chatBackBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_chatFromHomeFragment_to_homeFragment)
        }
        binding.sendBtn.setOnClickListener {
            viewModel.sendMessage(Utils.getUidLoggedIn(), args.recentchats.friendid!!, args.recentchats.name!!, args.recentchats.friendsimage!!)
        }
        viewModel.getMessages(args.recentchats.friendid!!).observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })
    }
    private fun initRecyclerView(list: List<Messages>) {
        adapter = MessageAdapter()

        val layoutManager = LinearLayoutManager(context)

        binding.messagesRecyclerView.layoutManager = layoutManager
        layoutManager.stackFromEnd = true

        adapter.setList(list)
        adapter.notifyDataSetChanged()
        binding.messagesRecyclerView.adapter = adapter
    }
}