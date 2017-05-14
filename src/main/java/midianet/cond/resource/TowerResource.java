package midianet.cond.resource;

import midianet.cond.domain.Tower;
import midianet.cond.exception.NotFoundException;
import midianet.cond.service.TowerService;
import midianet.cond.vo.TowerVO;
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
@RequestMapping("/resource/tower")
public class TowerResource {

    @Autowired
    private TowerService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TowerVO>> list() {
        final List<TowerVO> list = service.listAll()
                .stream().map(u -> TowerVO.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .build())
                .collect(Collectors.toList());
        if (list.isEmpty()) throw new NotFoundException("Torre", "");
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TowerVO> get(@PathVariable("id") final Long id) {
        return service.findById(id)
                .map(u -> new ResponseEntity(u, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException("Torre", id));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody final TowerVO tower, final UriComponentsBuilder ucBuilder) {
        final Tower u = service.create(Tower.builder()
                .name(tower.getName())
                .build());
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/resource/tower/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("id") long id, @Valid @RequestBody final TowerVO tower) {
        service.update(Tower.builder()
                .id(id)
                .name(tower.getName())
                .build());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}