package com.example.foodapp.presentation.screen.profile

import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentProfileBinding
import com.example.foodapp.domain.remote.model.UserInfo
import com.example.foodapp.presentation.common.base.BaseFragment
import com.example.foodapp.presentation.common.helper.Listener
import com.example.foodapp.presentation.extension.showSnackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate),
    Listener {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("users")

    override fun init() {
        showData()
        listeners()
    }

    private fun showData() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            databaseReference.child(user.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userInfo = snapshot.getValue(UserInfo::class.java)
                        binding.tvEmail.text = userInfo?.email
                    }

                    override fun onCancelled(error: DatabaseError) {
                        binding.root.showSnackbar(error.message)
                    }
                })
        }
    }

    override fun listeners() {
        binding.buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            showAuthFragment()
        }
    }

    private fun showAuthFragment() {
        val navController = findNavController()
        val action = ProfileFragmentDirections.actionProfileFragmentToAuthFragment()
        navController.navigate(action)
        navController.popBackStack(R.id.userFragment, false)
    }

}
