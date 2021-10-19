package com.myproject.myproject.datainterface;

import com.myproject.myproject.data.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInterface extends JpaRepository<Student, Integer>
{
    Student findByName(String name);
    Student findByIdAndName(int id,String name);
    
    Student findByIdAndEmail(int id,String email);


}
