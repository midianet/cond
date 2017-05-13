package midianet.cond.resource;

import midianet.cond.domain.User;
import midianet.cond.service.UserService;
import midianet.cond.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource/user")
public class UserResource {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserVO>> list() {
        final List<UserVO> list = service.listAll()
                .stream().map(u -> new UserVO(u.getId(), u.getName(), u.getUsername(), u.getPassword()))
                .collect(Collectors.toList());
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVO> get(@PathVariable("id") final Long id) {
        return service.findById(id)
                .map(u -> new ResponseEntity(u, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody final UserVO user, final UriComponentsBuilder ucBuilder) {
        //TODO: Iplmentar validação de unique username
        final User u = service.save(User.builder()
                                        .name(user
                                        .getName())
                                        .username(user.getUsername())
                                        .password(user.getPassword()).build());
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/rest/user/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserVO> update(@PathVariable("id") long id, @RequestBody final UserVO user) {
        final Optional<User> current = service.findById(id);
        current.ifPresent(u -> {
            u.setName(user.getName());
            u.setUsername(u.getUsername());
            u.setPassword(u.getPassword());
            service.save(u);
        });
        return current.map(usr -> new ResponseEntity(usr, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        final Optional<User> current = service.findById(id);
        current.ifPresent(u -> {
            service.delete(u.getId());
        });
        return current.map(usr -> new ResponseEntity(usr, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}