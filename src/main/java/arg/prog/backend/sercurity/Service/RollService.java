package arg.prog.backend.sercurity.Service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arg.prog.backend.sercurity.Entity.Roll;
import arg.prog.backend.sercurity.Enums.RollEnum;
import arg.prog.backend.sercurity.Repository.RollRepository;

@Service
@Transactional
public class RollService {
    @Autowired
    RollRepository rollRepository;
    public Optional <Roll> getByRollName(RollEnum rollNombre){
        return rollRepository.findByRollName(rollNombre);
    }
    public void save(Roll r)
    {
        rollRepository.save(r);
    }
    public boolean sinValores(){

        return rollRepository.findAll().isEmpty();
    }
}
