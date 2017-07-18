package midianet.cond.service;

import com.google.common.base.Strings;
import javaslang.control.Try;
import midianet.cond.domain.Ground;
import midianet.cond.exception.InfraException;
import midianet.cond.exception.NotFoundException;
import midianet.cond.repository.GroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static midianet.cond.domain.Ground.id;
import static midianet.cond.domain.Ground.nameStart;
import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.List;
import java.util.Optional;

@Service
public class GroundService {

    @Autowired
    private GroundRepository repository;

    @Transactional
    public Page<Ground> paginate(final String id, final String name, final PageRequest page) {
        javaslang.collection.List<Specification<Ground>> specs = javaslang.collection.List.empty();
        specs = Strings.isNullOrEmpty(id)   ? specs : specs.append(id(Long.parseLong(id)));
        specs = Strings.isNullOrEmpty(name) ? specs : specs.append(nameStart(name));
        final boolean noSpec = specs.isEmpty();
        final Specification<Ground> spec = noSpec ? null : specs.reduce((a1, a2) -> where(a1).and(a2));
        return Try.of(() -> noSpec ? repository.findAll(page) : repository.findAll(spec, page))
                .onFailure(e -> new InfraException(e))
                .get();
    }

    public List<Ground> listAll() {
        return Try.of(() -> repository.findAll(new Sort(Sort.Direction.ASC, "name")))
                .onFailure(InfraException::raise)
                .get();
    }

    public Optional<Ground> findById(final Long id) {
        return Try.of(() -> repository.findOne(id))
                .map(g -> Optional.ofNullable(g))
                .onFailure(InfraException::raise)
                .getOrElseThrow(() -> new NotFoundException("Ambiente", id));
    }

    @Transactional
    public Ground save(final Ground ground) {
        return Try.of(() -> repository.save(ground))
                .onFailure(InfraException::raise)
                .get();
    }

    @Transactional
    public void delete(final Long id) {
        final Ground old = findById(id)
                .orElseThrow(() -> new NotFoundException("Ambiente", id));
        Try.run(() -> repository.delete(old))
                .onFailure(InfraException::raise);
    }

    public Long count(){
        return Try.of(() -> new Long(repository.count()))
                .onFailure(InfraException::raise)
                .get();
    }

}