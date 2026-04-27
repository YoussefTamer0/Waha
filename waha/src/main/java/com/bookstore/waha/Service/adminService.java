package com.bookstore.waha.Service;

import com.bookstore.waha.model.Admin;
import com.bookstore.waha.repository.adminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class adminService {
    private final adminRepository adminrepo;

    public adminService(adminRepository adminrepo) {
        this.adminrepo = adminrepo;

    }

    public void addAdmin(Admin admin) {
        if (admin.getAdminID() == null)
            adminrepo.save(admin);
        else
            throw new RuntimeException("Admin already exists");
    }

    public void removeAdmin(Admin admin) {
        if (admin.getAdminID() == null)
            throw new RuntimeException("Admin doesnt exist");
        else
            adminrepo.delete(admin);
    }

    public List<Admin> getAllAdmin() {
        return adminrepo.findAll();
    }

    public Admin findAdminByID(Integer ID) {
        if (ID == null) {
            throw new RuntimeException("ID cannot be null");
        }

        Optional<Admin> adminOptional = adminrepo.findById(ID);

        if (adminOptional.isPresent()) {
            return adminOptional.get();
        } else {
            throw new RuntimeException("Admin doesn't exist with ID: " + ID);
        }
    }

}