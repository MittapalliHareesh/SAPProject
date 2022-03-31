package com.assignment.myapplication1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.myapplication1.adapter.ImageAdapter
import com.assignment.myapplication1.Image
import com.assignment.myapplication1.R
import com.assignment.myapplication1.databinding.FragmentSecondBinding
import com.assignment.myapplication1.util.InternetConnection
import com.assignment.myapplication1.util.Result
import com.assignment.myapplication1.util.Status
import com.assignment.myapplication1.viewModel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private val imageViewModel: ImageViewModel by viewModels()
    private lateinit var fragmentSecondBinding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSecondBinding = FragmentSecondBinding.inflate(inflater, container, false)
        loadImages()
        return fragmentSecondBinding.root
    }


    private fun loadImages() {
        if (InternetConnection.checkNetworkConnection(requireContext())) {
            populateImages()
        } else {
            fragmentSecondBinding.progressBar.visibility = View.GONE
            Toast.makeText(requireContext(), getString(R.string.noInternet), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun populateImages() {
        val bundle = Bundle()
        fragmentSecondBinding.recylerView.apply {
            adapter = ImageAdapter(object : ImageAdapter.OnItemClickListener {
                override fun onImageClick(image: Image) {
                    bundle.putString("tittle", image.tittle)
                    bundle.putString("URL", image.imageURL)
                    bundle.putString("description", image.description)
                    findNavController().navigate(
                        R.id.action_SecondFragment_to_ThirdFragment,
                        bundle
                    )
                }
            })
            layoutManager = LinearLayoutManager(context)
        }
        imageViewModel.getImages().observe(viewLifecycleOwner) {
            it?.let { resource ->
                renderUi(resource)
            }
        }
    }

    private fun renderUi(result: Result<List<Image>>) {
        imageViewModel.getImages().observe(viewLifecycleOwner) {
            when (result.state) {
                Status.SUCCESS -> {
                    fragmentSecondBinding.progressBar.visibility = View.GONE
                    fragmentSecondBinding.recylerView.visibility = View.VISIBLE
                    @Suppress("UNCHECKED_CAST")
                    (fragmentSecondBinding.recylerView.adapter as androidx.recyclerview.widget.ListAdapter<*, *>)
                        .submitList(result.data as List<Nothing>?)
                }

                Status.LOADING -> {
                    fragmentSecondBinding.progressBar.visibility = View.VISIBLE
                    fragmentSecondBinding.recylerView.visibility = View.GONE
                }

                Status.FAILURE -> {
                    fragmentSecondBinding.recylerView.visibility = View.VISIBLE
                    fragmentSecondBinding.progressBar.visibility = View.GONE
                    Toast.makeText(this.context, it.errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}