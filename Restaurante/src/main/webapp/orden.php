
<!DOCTYPE html>
<html>
<head>
    <title>TODO supply a title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script
        src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>
<div id="ordenes">
    <div class="container">
        <div align="center"><h3>Ordenes</h3></div>
        <br>
        <br>
        <div class="btn-group">
            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#newOrden">
                Generar orden
            </button>
            <br>
            <button type="button" class="btn btn-success mx-2" id="Busqueda" >
                Buscar orden
            </button>
        </div>
        <br>
        <br>
        <div class="modal fade" id="detalle" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Detalle de Orden # {{selectedItem.IdOrden}}</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">Descripción</th>
                                <th scope="col">Valor</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="menu in selectedItem.detalle">
                                <td v-text="menu.menu.IdMenu==0?menu.producto.Descripcion:menu.menu.Descripcion"></td>
                                <td v-text="menu.menu.IdProducto==0?menu.producto.Valor:menu.menu.ValorMenu"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Aceptar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="newOrden" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header" align="center">
                        <h5 class="modal-title" id="exampleModalLongTitle">Nueva orden</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" style="overflow-y: auto; height: 400px;">
                        <div v-if="errors.length" style="border-radius: 5px; background-color: #FF6F6F;color: white;padding: 10px">
                            <p>
                                <b>Por favor, verifique los siguientes errores:</b>
                            <ul>
                                <li v-for="error in errors">{{ error }}</li>
                            </ul>
                            </p>
                        </div>
                        <table class="table" id="menus">
                            <thead>
                            <tr>
                                <th>Descripción</th>
                                <th>Valor</th>
                                <th>Observaciones</th>
                                <th>Acción</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="menu in menus" v-bind:key="menu.IdMenu">
                                <td>{{menu.Descripcion}}</td>
                                <td>{{menu.ValorMenu}}</td>
                                <td>
                                    <input type="text" class="form-control" placeholder="Observaciones" aria-label="Observaciones" v-model="menu.Observacion">
                                </td>
                                <td><button class="btn btn-outline-secondary" type="button" v-on:click="addToBuy(menu)">Agregar</button></td>
                            </tr>
                            </tbody>
                        </table>
                        <div align="center"><h5>En carrito</h5></div>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Descripción</th>
                                <th>Valor</th>
                                <th>Observaciones</th>
                                <th>Cantidad</th>
                                <th>Acciones</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="(carItems,index) in orderToBuy">
                                <td>{{carItems.Descripcion}}</td>
                                <td>{{carItems.ValorMenu}}</td>
                                <td>{{carItems.Observacion}}</td>
                                <td>{{carItems.Cantidad}}</td>
                                <td>
                                    <button class="btn btn-info" v-on:click="eliminar(index)">
                                        <span class="glyphicon glyphicon-remove"></span> Borrar
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-dark" data-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="saveOrder()">Guardar</button>
                    </div>
                </div>
            </div>
        </div>
        <table class="table">
            <thead>
            <tr>
                <th>ID Orden</th>
                <th>Fecha</th>
                <th>Cocinada</th>
                <th>Pagada</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody id="trd">
            <tr v-for="orden in orders" v-bind:key="orden.IdOrden">
                <td>{{orden.IdOrden}}</td>
                <td>{{orden.Fecha}}</td>
                <td v-text="orden.Cocinada?'Si':'No'"></td>
                <td v-text="orden.Pagada?'Si':'No'"></td>
                <td>
                    <div class="btn-group">
                        <button class="btn btn-info" value="pagada" name="operation" v-if="!orden.Pagada" v-on:click="payOrder(orden.IdOrden)">Pagar</button>
                        <button class="btn btn-info" value="pagada" name="operation" data-toggle="modal" data-target="#detalle" v-on:click="selectItem(orden)">Detalles</button>
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Exportar
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                <button class="dropdown-item" type="button" v-on:click="exportOrder('json',orden.IdOrden)">Archivo JSON</button>
                                <button class="dropdown-item" type="button" v-on:click="exportOrder('xml',orden.IdOrden)">Archivo XML</button>
                                <button class="dropdown-item" type="button" v-on:click="exportOrder('txt',orden.IdOrden)">Archivo de texto</button>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.12"></script>
<script>

    //var orders_data=document.getElementById("orders_data").value;
    var app = new Vue({
        el: '#ordenes',
        data: {
            message: 'Hello Vue!',
            orders:<?php $response=file_get_contents("http://localhost:8080/Restaurante-1.0/Ordenes"); echo json_encode(json_decode($response)); ?>,
            products:<?php $response=file_get_contents("http://localhost:8080/Restaurante-1.0/Productos"); echo json_encode(json_decode($response)); ?>,
            menus:<?php $response=file_get_contents("http://localhost:8080/Restaurante-1.0/Menus"); echo json_encode(json_decode($response)); ?>,
            selectedItem:Object,
            orderToBuy:[],
            errors:[]
        },
        methods:{
            test(texto){
                console.log(texto);
            },
            async exportOrder(docType,idOrder){
                await axios.get(
                    `http://localhost:8080/Restaurante-1.0/Exportar?docType=${docType}&idOrder=${idOrder}`,
                    {
                        responseType: 'blob'
                    }
                ).then((response)=>{
                    var name="";

                    switch(response.data.type){
                        case "text/json":
                            name="ordenes.json";
                        break;
                        case "text/xml":
                            name="ordenes.xml";
                        break;
                        case "text/plain":
                            name="ordenes.txt";
                        break;
                    }

                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', name);
                    document.body.appendChild(link);
                    link.click();
                }).catch((error)=>{
                    console.log(error);
                });
            },
            async saveOrder(){
                const options = {
                    headers: {'Content-Type': 'application/json'}
                };

                await axios.post(
                    `http://localhost:8080/Restaurante-1.0/Ordenes?myJson=${JSON.stringify(this.orderToBuy)}`
                ).then((response)=>{
                    console.log(response);
                }).catch((error)=>{
                    console.log(error);
                });
            },
            async payOrder(IdOrden){
                await axios.put(
                    `http://localhost:8080/Restaurante-1.0/Ordenes?idOrden=${IdOrden}&accion=pagar`
                ).then((response)=>{
                    console.log(response);
                }).catch((error)=>{
                    console.log(error);
                });
            },
            eliminar(index){
                this.orderToBuy.splice(index,1);
            },
            addToBuy(item){
                this.errors=[];
                this.orderToBuy.push(JSON.parse(JSON.stringify(item)));
                item.Observacion="";
            },
            selectItem(orden){
                console.log(orden);
                this.selectedItem=orden;
            },
            /*async loadOrders() {
                let url1='http://localhost:8080/Restaurante-1.0/Ordenes';
                let url2='http://localhost:8080/Restaurante-1.0/Productos';
                let url3='http://localhost:8080/Restaurante-1.0/Menus';

                const request1=axios.get(url1);
                const request2=axios.get(url2);
                const request3=axios.get(url3);

                await axios.all([request1,request2,request3]).then(axios.spread((...responses)=>{
                   // this.orders=responses[0].data;
                    this.products=responses[1].data;
                    this.menus=responses[2].data;
                    console.log(responses);
                })).catch((error)=>{
                    console.log(error);
                });
            }*/
        },
        created(){
            //this.loadOrders();
        }
    })
</script>
</html>

