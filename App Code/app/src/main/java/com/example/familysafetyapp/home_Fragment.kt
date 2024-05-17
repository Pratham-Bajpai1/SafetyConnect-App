package com.example.familysafetyapp

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class home_Fragment : Fragment() {

    private val listContacts: ArrayList<ContactModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMembers = listOf<MemberData>(
            MemberData("Pratham"),
            MemberData("Uncu"),
            MemberData("Bauaa"),
            MemberData("Neeta"),
            MemberData("Rajesh"),
        )

        val adapter = Member_Items_Adapter(listMembers)
        val recycler = requireView().findViewById<RecyclerView>(R.id.member_recyclerView)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter


        /*val listContacts = listOf<ContactModel>(
            ContactModel("Nisha Trivedi", 9412376626),
            ContactModel("Ashok Mishra", 9425117678),
            ContactModel("Suman Mishra", 9988882211),
            ContactModel("Suan Mishra", 9988882211),
            ContactModel("Sman Mishra", 9988882211),
            ContactModel("Sumn Mishra", 9988882211),
            ContactModel("Suman Mshra", 9988882211)
        )*/



        val inviteAdapter = Invite_Adapter(listContacts)

        CoroutineScope(Dispatchers.IO).launch {
            listContacts.addAll(fetchContacts())
            insertDatabaseContacts(listContacts)
            withContext(Dispatchers.Main){
                inviteAdapter.notifyDataSetChanged()
            }
        }


        val inviteRecycle = requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        inviteRecycle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        inviteRecycle.adapter = inviteAdapter


    }

    private suspend fun insertDatabaseContacts(listContacts: ArrayList<ContactModel>) {

        val database = MyFamilyDatabase.getDatabase(requireContext())

        database.contactDao().insertAll(listContacts)
    }

    private fun fetchContacts(): ArrayList<ContactModel> {

        val cr = requireActivity ().contentResolver
        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        val listContacts: ArrayList<ContactModel> = ArrayList()

        if (cursor != null && cursor.count > 0) {

            while (cursor != null && cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if(hasPhoneNumber > 0){
                    val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ?", arrayOf(id), "")

                    if(pCur != null && pCur.count > 0){

                        while(pCur != null && pCur.moveToNext()){

                            val phoneNum = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                            listContacts.add(ContactModel(name, phoneNum))
                        }
                        pCur.close()
                    }
                }
            }
            if(cursor != null){
                cursor.close()
            }
        }
        return listContacts
    }

    companion object {

        @JvmStatic
        fun newInstance() = home_Fragment()
    }
}