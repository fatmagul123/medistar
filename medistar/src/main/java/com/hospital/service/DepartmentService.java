package com.hospital.service;

import com.hospital.dto.DepartmentCreateRequest;
import com.hospital.dto.DepartmentDetailResponse;
import com.hospital.dto.DepartmentResponse;
import com.hospital.dto.DepartmentUpdateRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.mapper.DepartmentMapper;
import com.hospital.mapper.DoctorMapper;
import com.hospital.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final DoctorMapper doctorMapper; // Doktor listelerini DTO'ya çevirmek için

    // --- STANDART CRUD İŞLEMLERİ ---

    @Transactional
    public DepartmentResponse createDepartment(DepartmentCreateRequest request) {
        // İsteğe bağlı: Aynı isimde departman var mı kontrolü
        if (departmentRepository.existsByName(request.getName())) {
            throw new RuntimeException("Bu isimde bir departman zaten mevcut!");
        }

        Department department = departmentMapper.toEntity(request);
        Department savedDepartment = departmentRepository.save(department);
        
        return departmentMapper.toResponse(savedDepartment);
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DepartmentDetailResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departman bulunamadı. ID: " + id));
                
        return departmentMapper.toDetailResponse(department);
    }

    @Transactional
    public DepartmentResponse updateDepartment(Long id, DepartmentUpdateRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departman bulunamadı. ID: " + id));

        departmentMapper.updateEntity(department, request);
        Department updatedDepartment = departmentRepository.save(department);
        
        return departmentMapper.toResponse(updatedDepartment);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Silinecek departman bulunamadı. ID: " + id);
        }
        departmentRepository.deleteById(id);
    }

    // --- ÖZEL ENDPOINT İŞLEMLERİ ---

    @Transactional(readOnly = true)
    public List<DoctorResponse> getDepartmentDoctors(Long deptId) {
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Departman bulunamadı. ID: " + deptId));

        return department.getDoctors().stream()
                .map(doctorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> getPriorityDoctors(Long deptId) {
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Departman bulunamadı. ID: " + deptId));

        // Not: Entity'nde doktorun "öncelikli" olup olmadığını belirten bir alan yok.
        // Şimdilik unvanında "Prof" veya "Doç" geçenleri öncelikli sayıyoruz. 
        // İleride Doctor entity'sine "boolean isPriority" eklersen burayı ona göre filtreleyebilirsin.
        return department.getDoctors().stream()
                .filter(doc -> doc.getTitle() != null && 
                       (doc.getTitle().contains("Prof") || doc.getTitle().contains("Doç")))
                .map(doctorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public String updateImage(Long deptId, String imageUrl) {
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Departman bulunamadı. ID: " + deptId));

        department.setImg(imageUrl);
        departmentRepository.save(department);
        
        return imageUrl;
    }
}
