package midianet.cond.resource;

import midianet.cond.domain.Ground;
import midianet.cond.exception.NotFoundException;
import midianet.cond.service.GroundService;
import midianet.cond.vo.GroundVO;
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
@RequestMapping("/resource/ground")
public class GroundResource {

    @Autowired
    private GroundService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GroundVO>> list() {
        final List<GroundVO> list = service.listAll()
                .stream().map(u -> GroundVO.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .build())
                .collect(Collectors.toList());
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroundVO> findById(@PathVariable("id") final Long id) {
        return service.findById(id)
                .map(g -> new ResponseEntity(GroundVO.builder()
                        .id(g.getId())
                        .name(g.getName())
                        .build(), HttpStatus.OK))
                .get();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody final GroundVO ground, final UriComponentsBuilder ucBuilder) {
        final Ground u = service.save(Ground.builder()
                .name(ground.getName())
                .build());
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/resource/ground/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("id") long id, @Valid @RequestBody final GroundVO ground) {
        service.save(Ground.builder()
                .id(id)
                .name(ground.getName())
                .build());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}