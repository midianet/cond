package midianet.cond.resource;

import midianet.cond.domain.Ground;
import midianet.cond.service.GroundService;
import midianet.cond.vo.GroundVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource/ground")
public class GroundResource {

    @Autowired
    private GroundService service;

    @RequestMapping(value = "/page", method = RequestMethod.GET , produces = DataTableResponse.JSON)
    public ResponseEntity<DataTableResponse> paginate(@RequestParam("draw")                      final Long    draw,
                                                      @RequestParam("start")                     final Long    start,
                                                      @RequestParam("length")                    final Integer length,
                                                      @RequestParam("search[value]")             final String  searchValue,
                                                      @RequestParam("columns[0][search][value]") final String  id,
                                                      @RequestParam("columns[1][search][value]") final String  name,
                                                      @RequestParam("order[0][column]")          final Integer ordem,
                                                      @RequestParam("order[0][dir]")             final String  ordemDir){
        final String[] columns = new String[]{"id", "name"};
        final List<String[]> data = new ArrayList();
        final DataTableResponse dt = new DataTableResponse();
        dt.setDraw(draw);
        try {
            final Long qtTotal = service.count();
            final Map<String, String> searchParams = new HashMap();
            if (!searchValue.isEmpty()) {
                searchParams.put(columns[1], searchValue);
            }
            final Integer page   = new Double(Math.ceil(start / length)).intValue();
            final PageRequest pr = new PageRequest(page,length, new Sort(new Sort.Order(Sort.Direction.fromString(ordemDir),columns[ordem])));
            final Page<GroundVO> list = service.paginate(id, name, pr).map(g -> GroundVO.builder().id(g.getId()).name(g.getName()).build());
            final Long qtFilter  = list.getTotalElements();
            if (qtFilter > 0) {
                list.forEach(r -> data.add(r.toArray()));
            }
            dt.setRecordsFiltered(qtFilter);
            dt.setData(data);
            dt.setRecordsTotal(qtTotal);
        } catch (Exception e) {
            System.out.println(e);
            dt.setError("Datatable error "+ e.getMessage());
        }
        return new ResponseEntity(dt, HttpStatus.OK);

    }

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