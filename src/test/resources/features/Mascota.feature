Feature: Gestionar mascotas

  Como administrador de una tienda de mascotas
  Quiero gestionar las mascotas de la tienda
  Para llevar un control de las mascotas

  Scenario:Agregar una mascota nueva
    Given que se prepara la tienda de mascotas
    When se añade la mascota nro 193 de nombre "perro1"
    Then se valida creacion de nueva mascota

  Scenario: Consultar mascota creada
    Given que se prepara la tienda de mascotas
    When se consulta la mascota creada nro 193
    Then se valida consulta de mascota

  Scenario: Actualizar nombre de mascota
    Given que se prepara la tienda de mascotas
    When se cambia nombre de mascota nro 193 a "poseidon"
    Then se valida cambio de nombre

  Scenario: Eliminar mascota de la lista
    Given que se prepara la tienda de mascotas
    When se elimina a la mascota nro 193
    Then se valida eliminacion de mascota