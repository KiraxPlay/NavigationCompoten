package com.example.navigationcomponentexample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationcomponentexample.adapters.UserAdapter
import com.example.navigationcomponentexample.databinding.FragmentUserCrudBinding
import com.example.navigationcomponentexample.model.providers.UserDatabaseProvider
import com.example.navigationcomponentexample.model.entities.UserEntity
import kotlinx.coroutines.launch

class UserCrudFragment : Fragment() {

    private var _binding: FragmentUserCrudBinding? = null
    private val binding get() = _binding!!

    private val userDao by lazy {
        UserDatabaseProvider.getDatabase(requireContext()).getUserDao()
    }

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserCrudBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        cargarUsuarios()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }

    private fun setupListeners() {
        // Agregar usuario
        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val apellido = binding.etApellido.text.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty()) {
                val nuevoUsuario = UserEntity(nombre = nombre, apellido = apellido)
                lifecycleScope.launch {
                    userDao.insertar(nuevoUsuario)
                    cargarUsuarios()
                    Toast.makeText(context, "Usuario agregado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Complete los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Actualizar usuario
        binding.btnActualizar.setOnClickListener {
            val userId = binding.etUserId.text.toString().toIntOrNull() // Obtener ID del campo
            val nuevoNombre = binding.etNombre.text.toString()
            val nuevoApellido = binding.etApellido.text.toString()

            if (userId != null && nuevoNombre.isNotEmpty() && nuevoApellido.isNotEmpty()) {
                lifecycleScope.launch {
                    val usuario = userDao.getUserById(userId)
                    if (usuario != null) {
                        val usuarioActualizado = usuario.copy(nombre = nuevoNombre, apellido = nuevoApellido)
                        userDao.actualizar(usuarioActualizado)
                        cargarUsuarios()
                        Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Eliminar usuario
        binding.btnEliminar.setOnClickListener {
            val userId = binding.etUserId.text.toString().toIntOrNull() // Obtener ID del campo

            if (userId != null) {
                lifecycleScope.launch {
                    val usuario = userDao.getUserById(userId)
                    if (usuario != null) {
                        userDao.eliminarUsuario(usuario)
                        cargarUsuarios()
                        Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarUsuarios() {
        lifecycleScope.launch {
            val usuarios = userDao.getAllUsers()
            userAdapter.submitList(usuarios)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
