package midianet.cond.resource;

import midianet.cond.domain.User;
import midianet.cond.exception.NotFoundException;
import midianet.cond.service.UserService;
import midianet.cond.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource/user")
public class UserResource {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserVO>> list() {
        final List<UserVO> list = service.listAll()
                .stream().map(u -> UserVO.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .username(u.getUsername())
                        .password(u.getPassword())
                        .build())
                .collect(Collectors.toList());
        if (list.isEmpty()) throw new NotFoundException("Usuário", "");
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVO> get(@PathVariable("id") final Long id) {
        return service.findById(id)
                .map(u -> new ResponseEntity(u, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException("Usuário", id));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody final UserVO user, final UriComponentsBuilder ucBuilder) {
        final User u = service.create(User.builder()
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .build());
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/resource/user/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("id") long id, @Valid @RequestBody final UserVO user) {
        service.update(User.builder()
                .id(id)
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .build());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}