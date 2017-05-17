package midianet.cond.service;

import javaslang.control.Try;
import midianet.cond.domain.Resident;
import midianet.cond.exception.InfraException;
import midianet.cond.exception.NotFoundException;
import midianet.cond.exception.TelegramUsedException;
import midianet.cond.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {

    @Autowired
    private ResidentRepository repository;

    @Transactional
    public Page<Resident> listAll(final Long id, final String name, final String residentname, final PageRequest page) {
//        javaslang.collection.List<Specification<Resident>> specs = javaslang.collection.List.empty();
//        specs = id != null && id > 0 ? specs.append(id(id)) : specs;
//        specs = Strings.isNullOrEmpty(name) ? specs : specs.append(nameStart(name));
//        specs = Strings.isNullOrEmpty(residentname) ? specs : specs.append(residentnameStart(residentname));
//        final boolean noSpec = specs.isEmpty();
//        final Specification<Resident> spec = noSpec ? null : specs.reduce((a1, a2) -> where(a1).and(a2));
//        return Try.of(() -> noSpec ? repository.findAll(page) : repository.findAll(spec, page))
//                .onFailure(e -> new InfraException(e))
//                .get();
        return null;
    }

    public List<Resident> listAll() {
        return Try.of(() -> repository.findAll(new Sort(Sort.Direction.ASC, "name")))
                .onFailure(InfraException::raise)
                .get();
    }

    public Optional<Resident> findById(final Long id) {
        return Try.of(() -> repository.findOne(id))
                .map(u -> Optional.ofNullable(u))
                .onFailure(InfraException::raise)
                .getOrElseThrow(() -> new NotFoundException("Morador", id));
    }

    @Transactional
    public Resident save(final Resident resident) {
        if (resident.getId() == null) {
            return create(resident);
        } else {
            return update(resident);
        }
    }


    private Resident update(final Resident resident) {
        final Resident old = findById(resident.getId())
                .orElseThrow(() -> new NotFoundException("Morador", resident.getId()));
        old.setName(resident.getName());
        old.setApartment(resident.getApartment());
        old.setTower(resident.getTower());
        old.setEnd(resident.getEnd());
        return Try.of(() -> repository.save(old))
                .onFailure(InfraException::raise)
                .get();
    }

    private Resident create(final Resident resident) {
        if (!repository.findByTelegram(resident.getTelegram()).isEmpty())
            throw new TelegramUsedException(resident.getTelegram());
        return Try.of(() -> repository.save(resident))
                .onFailure(InfraException::raise)
                .get();
    }

    @Transactional
    public void delete(final Long id) {
        final Resident old = findById(id)
                .orElseThrow(() -> new NotFoundException("Morador", id));
        Try.run(() -> repository.delete(old))
                .onFailure(InfraException::raise);
    }

}