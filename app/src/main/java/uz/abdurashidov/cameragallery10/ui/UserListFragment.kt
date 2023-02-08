package uz.abdurashidov.cameragallery10.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.abdurashidov.cameragallery10.R
import uz.abdurashidov.cameragallery10.adapters.RvAdapter
import uz.abdurashidov.cameragallery10.databinding.FragmentUserListBinding
import uz.abdurashidov.cameragallery10.db.MyDbHelper
import uz.abdurashidov.cameragallery10.models.User


class UserListFragment : Fragment() {

    private val binding by lazy { FragmentUserListBinding.inflate(layoutInflater) }
    private lateinit var rvAdapter: RvAdapter
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list: ArrayList<User>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myDbHelper = MyDbHelper(binding.root.context)
        list = ArrayList()
        list.addAll(myDbHelper.getAllUsers())
        rvAdapter = RvAdapter(list)
        binding.myRv.adapter = rvAdapter

        binding.toolbarImage.setOnClickListener {
            findNavController().navigate(R.id.addUserFragment)
        }
        return binding.root
    }

}