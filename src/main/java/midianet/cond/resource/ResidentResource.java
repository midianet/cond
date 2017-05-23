package midianet.cond.resource;

import midianet.cond.domain.Resident;
import midianet.cond.exception.NotFoundException;
import midianet.cond.service.ResidentService;
import midianet.cond.vo.ResidentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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

@RestController
@RequestMapping("/resource/resident")
public class ResidentResource {

    @Autowired
    private ResidentService service;

    @RequestMapping(value = "/paginar", method = RequestMethod.GET, produces = DataTableResponse.JSON)
    public ResponseEntity<DataTableResponse> list(@RequestParam("draw")                      final Integer draw,
                                                  @RequestParam("start")                     final Integer start,
                                                  @RequestParam("length")                    final Integer length,
                                                  @RequestParam("search[value]")             final String  searchValue,
                                                  @RequestParam("columns[0][search][value]") final String  torre,
                                                  @RequestParam("columns[1][search][value]") final String  apto,
                                                  @RequestParam("columns[2][search][value]") final String  nome,
                                                  @RequestParam("order[0][column]")          final Integer ordem,
                                                  @RequestParam("order[0][dir]")             final String  ordemDir) {

        final DataTableResponse dtr = new DataTableResponse();
        final List<Map<String, String>> res = new ArrayList();
        dtr.setDraw(draw);
        final String[] columns = new String[]{"torre", "apto","nome"};
        try {
            final Integer qtTotal = new Long(service.count()).intValue();
            final Map<String, String> searchParams = new HashMap();
            if (!searchValue.isEmpty()) {
                searchParams.put(columns[1], searchValue);
            }
            final Integer page = new Double(Math.ceil(start / length)).intValue();
            final PageRequest pr = new PageRequest(page, length,
                    new Sort(new Order(Direction.fromString(ordemDir), columns[ordem])));

            final Page<ResidentVO> list = service.paginate(Long.parseLong(torre), apto, nome, pr).map(r -> ResidentVO.builder().id(r.getId()).apartment(r.getApartment()).name(r.getName()).build()) ;

            final Integer qtFilter = new Long(list.getTotalElements()).intValue();
            if (qtFilter > 0) {
                list.forEach(r -> res.add(r.asMapofValues((Object v) -> String.format("row_%s", v),
                                                                        "DT_RowId",
                                                                        "id",
                                                                        columns
                )));
            }
            dtr.setRecordsFiltered(qtFilter);
            dtr.setData(res);
            dtr.setRecordsTotal(qtTotal);
        } catch (Exception e) {
            //dtr.setError(GoiasResourceMessage.getMessage("msg_erro_dessconhecido:"+ e.getMessage()));
        }
        return new ResponseEntity(dtr, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResidentVO> findById(@PathVariable("id") final Long id) {
        return service.findById(id)
                .map(u -> new ResponseEntity(u, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException("Usuário", id));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody final ResidentVO resident, final UriComponentsBuilder ucBuilder) {
        final Resident r = service.save(Resident.builder().build());
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/resource/user/{id}").buildAndExpand(r.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("id") long id, @Valid @RequestBody final ResidentVO resident) {
        service.save(Resident.builder().build());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}