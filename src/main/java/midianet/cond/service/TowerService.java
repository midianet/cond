package midianet.cond.service;

import javaslang.control.Try;
import midianet.cond.domain.Tower;
import midianet.cond.exception.InfraException;
import midianet.cond.exception.NotFoundException;
import midianet.cond.repository.TowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TowerService {

    @Autowired
    private TowerRepository repository;

    @Transactional
    public Page<Tower> listAll(final Long id, final String name, final PageRequest page) {
//        javaslang.collection.List<Specification<User>> specs = javaslang.collection.List.empty();
//        specs = id != null && id > 0 ? specs.append(id(id)) : specs;
//        specs = Strings.isNullOrEmpty(name) ? specs : specs.append(nameStart(name));
//        specs = Strings.isNullOrEmpty(username) ? specs : specs.append(usernameStart(username));
//        final boolean noSpec = specs.isEmpty();
//        final Specification<User> spec = noSpec ? null : specs.reduce((a1, a2) -> where(a1).and(a2));
//        return Try.of(() -> noSpec ? repository.findAll(page) : repository.findAll(spec, page))
//                .onFailure(e -> new InfraException(e))
//                .get();
        return null;
    }

    public List<Tower> listAll() {
        return Try.of(() -> repository.findAll(new Sort(Sort.Direction.ASC, "name")))
                .onFailure(InfraException::raise)
                .get();
    }

    public Optional<Tower> findById(final Long id) {
        return Try.of(() -> repository.findById(id))
                .onFailure(InfraException::raise)
                .getOrElseThrow(() -> new NotFoundException("Torre", id));
    }

    @Transactional
    public Tower update(final Tower tower) {
        final Tower old = findById(tower.getId())
                .orElseThrow(() -> new NotFoundException("Torre", tower.getId()));
        old.setName(tower.getName());
        return Try.of(() -> repository.save(old))
                .onFailure(InfraException::raise)
                .get();
    }

    @Transactional
    public Tower create(final Tower tower) {
        return Try.of(() -> repository.save(tower))
                .onFailure(InfraException::raise)
                .get();
    }

    @Transactional
    public void delete(final Long id) {
        Try.run(() -> findById(id).ifPresent(a -> repository.delete(a)))
                .onFailure(InfraException::raise);
    }

}