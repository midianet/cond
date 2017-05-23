# go-data-table

Diretiva AngularJs para criação de tabela com lista paginada.

```html
                        <table go-data-table edit-ctl="edit" ajax="../api/aluno/paginar" id="dataTables-alunos" enabler-btn-row-edit="editavel"
                               class="display table table-hover" cellspacing="0" width="100%">
                            <thead>
                            <tr style="background: #F5F5F5;">
                                <th go-data-table-item name="id" style="width: 5%">Id</th>
                                <th go-data-table-item name="nome" style="width: 40%">Nome</th>
                                <th go-data-table-item name="email" style="width: 15%">Email</th>
                                <th style="width: 5%"></th>
                            </tr>
                            <tr style="background: #F5F5F5;">
                                <th><input id="id-search" type="text" placeholder="Id"/></th>
                                <th><input id="nome-search" type="text" placeholder="Filtrar por Nome"/></th>
                                <th><input id="email-search" type="text" placeholder="Filtrar por Email"/></th>
                                <th></th>
                            </tr>
                            </thead>
                        </table>
```
## id

Deve ser informado, o componente html é manipulado pela diretiva através do seu `id`.

## edit-ctl

Função Java Script que assuma a manipulação da edição, é passado como parâmetro o `id` do objeto, quando o usuário acessa a função através do botão editar.

## editUrl _opcional_

```html
                        <table go-data-table editUrl="../edita"
```                        
Quando informado, o componente redireciona para a url passando como parâmetro na url o `id` do objeto. 
Exemplo: ..."/edita?id=10"
Opcionalmente, pode ser usado `#` no começo da url para indicar que a url deve ser manipulada pelo router do Angular.


## name

O `name` do `go-data-table-item` deve ser a propriedade do objeto de retorno em `ajax`.

## search

O `id` do filtro deve ser `[name do go-data-table-item]-search`. Todos `go-data-table-item` deve ter um search equivalente.

## enabler-btn-row-edit

Quando informado, o componente deixa a cargo do valor da variável do elemento, caso igual a "True" mostra o botão editar. 
No exemplo a cima irá inspecionar o valor de `editavel` para cada dado elemento retornado da consulta ajax, que alimenta as informações das colunas do row.