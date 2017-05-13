package midianet.cond.service;

import javaslang.control.Try;
import midianet.cond.domain.User;
import midianet.cond.exception.InfraException;
import midianet.cond.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public Page<User> listAll(final Long id, final String name, final String username, final PageRequest page) {
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

    public List<User> listAll() {
        return Try.of(() -> repository.findAll())
                .onFailure(InfraException::raise)
                .get();
    }

    public Optional<User> findById(final Long id) {
        return Try.of(() -> repository.findOne(id))
                .map(e -> Optional.ofNullable(e))
                .onFailure(InfraException::raise)
                .get();
    }

    @Transactional
    public User save(final User user) {
        return Try.of(() -> repository.save(user))
                .onFailure(InfraException::raise)
                .get();
    }

    @Transactional
    public void delete(final Long id) {
        Try.run(() -> findById(id).ifPresent(a -> repository.delete(a)))
            .onFailure(InfraException::raise);
    }

}