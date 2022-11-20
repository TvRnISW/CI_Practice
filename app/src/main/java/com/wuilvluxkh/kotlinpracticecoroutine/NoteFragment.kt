package com.wuilvluxkh.kotlinpracticecoroutine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wuilvluxkh.kotlinpracticecoroutine.databinding.FragmentNoteBinding

class NoteFragment : Fragment() {

    private var _binding : FragmentNoteBinding? = null
    private val binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        Log.d("TAG", "onCreateView: This is another log")
        Log.d("TAG", "onCreateView: This is another log")
        Log.d("TAG", "onCreateView: This is another log")
        Log.d("TAG", "onCreateView: This is another log")
        Log.d("TAG", "onCreateView: This is another log")
        Log.d("TAG", "onCreateView: This is another log")
        return binding.root
    }

    // add new comment
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
    }
}