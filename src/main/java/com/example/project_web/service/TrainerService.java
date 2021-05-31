package com.example.project_web.service;

import com.example.project_web.entity.Invoice;
import com.example.project_web.entity.Trainer;
import com.example.project_web.repository.InvoiceRepository;
import com.example.project_web.repository.Trainerrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private Trainerrepository trainerrepository;

    // Lấy toàn bộ danh sách trainer
    public List<Trainer> findAll() {
        return trainerrepository.findAll();
    }

    // Tìm trainer theo id
    public Trainer findTrainerById(int id) {
        return trainerrepository.findTrainerById(id);
    }

    //Thêm trainer mới
    public Trainer addTrainer(Trainer trainer) {
        return trainerrepository.save(trainer);
    }

    // Chỉnh sửa thông tin trainer
    public void update(Trainer trainer) {
        Trainer t = trainerrepository.findTrainerById(trainer.getId());
        t.setName(trainer.getName());
        t.setPhone(trainer.getPhone());
        t.setDob(trainer.getDob());
        t.setFb_link(trainer.getFb_link());
        t.setIns_link(trainer.getIns_link());
        t.setTw_link(trainer.getTw_link());
        t.setDescription(trainer.getDescription());
        trainerrepository.save(t);
    }

    // Xóa trainer theo id
    public void deleteById(int id) {
        trainerrepository.deleteById(id);
    }
}
