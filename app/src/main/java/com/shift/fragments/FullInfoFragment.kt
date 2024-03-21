package com.shift.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.shift.R
import com.shift.adapter.UserAdapter
import com.shift.model.User
import com.squareup.picasso.Picasso


class FullInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_full_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }


        val user = requireArguments().getSerializable("user") as User

        val avatar  = view.findViewById<ImageView>(R.id.avatar)
        val name  = view.findViewById<TextView>(R.id.name)
        val address = view.findViewById<TextView>(R.id.address)
        val phoneNumber = view.findViewById<TextView>(R.id.number)
        val country = view.findViewById<TextView>(R.id.counry)
        val gender = view.findViewById<TextView>(R.id.gender)
        val postcode = view.findViewById<TextView>(R.id.postindex)
        val age = view.findViewById<TextView>(R.id.age)
        val email = view.findViewById<TextView>(R.id.email)
        val cords = view.findViewById<TextView>(R.id.cords)

        name.text = "${user.name}"
        address.text = "${user.location.street.name} ${user.location.street.number}"
        country.text = "${user.location.city}, ${user.location.country}"
        phoneNumber.text = "${user.phone}"

        val genderResource = if (user.gender == "male") R.string.gender_male else R.string.gender_female
        val Age = R.string.age

        gender.text = getString(genderResource)
        postcode.text = "${user.location.postcode}"
        age.text = "${getString(Age)} ${user.dob.age}"
        email.text = "${user.email}"
        cords.text = "${user.location.coordinates.latitude}, ${user.location.coordinates.longitude}"

        Picasso.get()
            .load(user.picture.medium)
            .into(avatar)


        email.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${user.email}")
            }
            if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(emailIntent)
            }
        }

        phoneNumber.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${user.phone}")
            }
            if (dialIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(dialIntent)
            }
        }

        address.setOnClickListener {
            val addressUri = Uri.parse("geo:0,0?q=${user.location.street.number} ${user.location.street.name}")
            val mapIntent = Intent(Intent.ACTION_VIEW, addressUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(mapIntent)
            }
        }

        cords.setOnClickListener {
            val geoUri = Uri.parse("geo:${user.location.coordinates.latitude},${user.location.coordinates.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(mapIntent)
            }
        }





    }
}