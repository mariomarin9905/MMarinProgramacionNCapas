package com.digis01.MMarinProgrmacionNCapasSpring.controller;

import com.digis01.MMarinProgrmacionNCapasSpring.DAO.ColoniaDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.DAO.DireccionDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.DAO.EstadoDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.DAO.MunicipioDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.DAO.PaisDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.DAO.RolDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.DAO.UsuarioDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Colonia;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Municipio;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Pais;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.ResultFile;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Rol;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Usuario;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.UsuarioDireccion;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImplementation UsuarioDAOImplementacion;

    @Autowired
    private RolDAOImplementation RolDAOImplementation;
    @Autowired
    private PaisDAOImplementation PaisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation EstadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping
    public String UsuarioIndex(Model model, @RequestParam(required = false) String nombre, @RequestParam(required = false) String apellidoPaterno, @RequestParam(required = false) String apellidoMaterno, @RequestParam(required = false) String idRol) {
        Result result = new Result();
        if ((nombre != null) || (apellidoPaterno != null) || (apellidoMaterno != null) || (idRol != null)) {
            if (idRol == null) {
                idRol = "0";
            }
            result = UsuarioDAOImplementacion.UsuarioGetAllDinamico(nombre, apellidoPaterno, apellidoMaterno, Integer.parseInt(idRol));
        } else {
            result = this.UsuarioDAOImplementacion.UsuarioGetAllJPA();
            //result = UsuarioDAOImplementacion.UsuarioGetAllDinamico("", "", "", 0);
        }

        Usuario usuairoBusqueda = new Usuario();
        model.addAttribute("roles", RolDAOImplementation.RolGetAll().object);
        model.addAttribute("usuarioBusqueda", usuairoBusqueda);
        model.addAttribute("listaUsuario", result.objects);
        return "UsuarioIndex";
    }

    @PostMapping("GetAllDinamico")
    public String UsuarioIndex(@ModelAttribute Usuario usuario) {

        return "redirect:/Usuario?nombre=" + usuario.getNombre() + "&apellidoPaterno=" + usuario.getApellidoPaterno() + "&apellidoMaterno=" + usuario.getApellidoMaterno() + "&idRol=" + Integer.toString(usuario.rol.getIdRol());

    }

    @GetMapping("form/{IdUsuario}")
    public String UsuarioAdd(@PathVariable int IdUsuario, Model model) {

        if (IdUsuario == 0) {
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.rol = new Rol();
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.Colonia = new Colonia();
            usuarioDireccion.Direccion.Colonia.Municipio = new Municipio();
            usuarioDireccion.Direccion.Colonia.Municipio.Estado = new Estado();
            usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais = new Pais();
            model.addAttribute("paises", this.PaisDAOImplementation.PaisGetAllJPA().objects);
            model.addAttribute("roles", RolDAOImplementation.RolGetAllJPA().objects);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "Form";
        } else {
            Result result = UsuarioDAOImplementacion.DireccionesByIdUsuarioJPA(IdUsuario);
            model.addAttribute("usuarioDireccion", result.object);
            return "UsuarioUpdate";
        }
    }

    @GetMapping("FormEditable")
    public String UsuarioUpdate(@RequestParam int IdUsuario, @RequestParam(required = false) Integer IdDireccion, Model model) {

        if (IdDireccion == null) {
            //Editar usuario
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = (Usuario) this.UsuarioDAOImplementacion.UsuarioByIdJPA(IdUsuario).object;
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(-1);
            model.addAttribute("roles", RolDAOImplementation.RolGetAll().object);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
           
        } else if (IdDireccion == 0) {
            //Agregar direccion
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Usuario.rol = new Rol();
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.Colonia = new Colonia();
            model.addAttribute("paises", this.PaisDAOImplementation.PaisGetAll().objects);
            model.addAttribute("usuarioDireccion", usuarioDireccion);           
        } else {
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Usuario.rol = new Rol();
            usuarioDireccion.Direccion = (Direccion) this.direccionDAOImplementation.DireccionGetByIdJPA(IdDireccion).object;
            int idPais = usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais();
            int idEstado = usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado();
            int idMunicipio = usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio();
            model.addAttribute("paises", this.PaisDAOImplementation.PaisGetAll().objects);
            model.addAttribute("estados", this.EstadoDAOImplementation.EstadoByIdPais(idPais).objects);
            model.addAttribute("municipios", this.municipioDAOImplementation.MunicipioByIdEstado(idEstado).objects);
            model.addAttribute("colonias", this.coloniaDAOImplementation.ColoniaBydIdMunicipio(idMunicipio).objects);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            
        }
        return "Form";
    }

    @PostMapping("Form")
    public String UsaurioAdd(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion, BindingResult BindingResult, @RequestParam MultipartFile imagenFile, Model model) {
        /*
        boolean bandera = false;
        String error = null;
        if (usuarioDireccion.Usuario.getFechaNacimiento() != null) {
            Date nacimiento = usuarioDireccion.Usuario.getFechaNacimiento();
            //Modificar
            int fechaActual = Year.now().getValue();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nacimiento);
            int fechaNacimiento = calendar.get(Calendar.YEAR);
            int result = fechaActual - fechaNacimiento;
            if (result < 18) {
                error = "Tines que tener 18 años o ser mayor";
                model.addAttribute("errorFecha", error);
                bandera = true;
            }

        }
        if (BindingResult.hasErrors() || bandera) {
           
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "Form";
        } else {
            UsuarioDAOImplementacion.UsuarioAdd(usuarioDireccion);
            return "redirect";
        }
        
         */
        try {
            if (!imagenFile.isEmpty()) {
                byte[] bytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
                usuarioDireccion.Usuario.setImagen(imgBase64);
            }
        } catch (Exception e) {

        }
        if (usuarioDireccion.Usuario.getIdUsuario() == 0) {
            //Agrega usuario
            usuarioDireccion.Usuario.setEstatus(1);
            //UsuarioDAOImplementacion.UsuarioAdd(usuarioDireccion);
            this.UsuarioDAOImplementacion.UsuarioAddJPA(usuarioDireccion);
        } else {
            if (usuarioDireccion.Direccion.getIdDireccion() == -1) {
                //Actualiza Usuario 
                //this.UsuarioDAOImplementacion.UsuarioUpdate(usuarioDireccion.Usuario);
                this.UsuarioDAOImplementacion.UsuarioUpdateJPA(usuarioDireccion.Usuario);
            } else if (usuarioDireccion.Direccion.getIdDireccion() == 0) {
                //Agrega Direccion
               // this.direccionDAOImplementation.DireccionAdd(usuarioDireccion.Usuario.getIdUsuario(), usuarioDireccion.Direccion);
               this.direccionDAOImplementation.DireccionAddJPA(usuarioDireccion.Usuario.getIdUsuario(), usuarioDireccion.Direccion);
            } else {
                //Actualiza Direccion
                //this.direccionDAOImplementation.DireccionUpdate(usuarioDireccion.Usuario.getIdUsuario(), usuarioDireccion.Direccion);
                this.direccionDAOImplementation.DireccionUpdateJPA(usuarioDireccion.Usuario.getIdUsuario(), usuarioDireccion.Direccion);                
            }
        }
        return "redirect:/Usuario/form/"+usuarioDireccion.Usuario.getIdUsuario();
    }

    @DeleteMapping("UsuarioDelete/{IdUsuario}")
    @ResponseBody
    public Result UsuarioDelete(@PathVariable int IdUsuario) {
        //Result result = this.UsuarioDAOImplementacion.UsuarioDireccionDelete(IdUsuario);
        Result result = this.UsuarioDAOImplementacion.UsuarioDireccionDeleteJPA(IdUsuario);
        return result;
    }

    @DeleteMapping("DreccionDelete/{IdDireccion}")
    @ResponseBody
    public Result DireccionDelete(@PathVariable int IdDireccion) {
        //Result result = this.direccionDAOImplementation.DireccionDelete(IdDireccion);
        Result result = this.direccionDAOImplementation.DireccionDeleteJPA(IdDireccion);
        return result;
    }

    @GetMapping("EstadoByIdPais/{IdPais}")
    @ResponseBody
    public Result EstadoByIdPais(@PathVariable int IdPais) {
       // Result result = EstadoDAOImplementation.EstadoByIdPais(IdPais);
       Result result = EstadoDAOImplementation.EstadoByIdPaisJPA(IdPais);
        return result;
    }

    @GetMapping("MunicipioByIdEstado/{IdEstado}")
    @ResponseBody
    public Result MunicipioByIdEstado(@PathVariable int IdEstado) {
        //Result result = this.municipioDAOImplementation.MunicipioByIdEstado(IdEstado);
        Result result = this.municipioDAOImplementation.MunicipioByIdEstadoJPA(IdEstado);
        return result;
    }

    @GetMapping("ColoniaByIdMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result ColoniaBydIdMunicipio(@PathVariable int IdMunicipio) {
        Result result = this.coloniaDAOImplementation.ColoniaBydIdMunicipio(IdMunicipio);
        return result;
    }

    @GetMapping("ColoniaByCodigoPostal/{CodigoPostal}")
    @ResponseBody
    public Result ColoniaByCodigoPostal(@PathVariable String CodigoPostal) {
        Result result = this.coloniaDAOImplementation.ColoniaByCodigoPostal(CodigoPostal);
        return result;
    }

    @PostMapping("UsuarioUpdateByEstatus")
    @ResponseBody
    public Result UsuarioUpdateByEstatus(@RequestParam int IdUsuario, @RequestParam int Estatus) {
        Result result = this.UsuarioDAOImplementacion.UsuarioUpdateByEstatus(IdUsuario, Estatus);
        return result;
    }

    @GetMapping("CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("CargaMasiva")
    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) {
        try {
            if (!archivo.isEmpty()) {
                String root = System.getProperty("user.dir");
                String path = "src/main/resources/static/archivos";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
                archivo.transferTo(new File(absolutePath));//Guarda archivo
                String tipoArchivo = archivo.getOriginalFilename().split("\\.")[1];
                List<UsuarioDireccion> usuarios = null;
                if (tipoArchivo.equals("txt")) {
                    usuarios = this.LecturaArchivoTXT(new File(absolutePath));
                } else {
                    usuarios = this.LecturaEXCEL(new File(absolutePath));
                }
                List<ResultFile> listaErrores = this.ValidaArchivos(usuarios);
                if (listaErrores.isEmpty()) {
                    //Si es vacio se guarda la ruta del archivo
                    session.setAttribute("urlFile", absolutePath);
                    model.addAttribute("listaErrores", listaErrores);
                } else {
                    //Es vacia no se guarda ruta
                    model.addAttribute("listaErrores", listaErrores);
                }
            }

        } catch (Exception e) {
            return "redirect:/Usuario/CargaMasiva";
        }
        return "CargaMasiva";
    }

    private List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {
        List<UsuarioDireccion> usuarios = new ArrayList<>();
        try (FileReader fileReader = new FileReader(archivo); BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] campos = linea.split("\\|");
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setNombre(campos[0]);
                usuarioDireccion.Usuario.setApellidoPaterno(campos[1]);
                usuarioDireccion.Usuario.setApellidoMaterno(campos[2]);
                usuarioDireccion.Usuario.setUserName(campos[3]);
                usuarioDireccion.Usuario.setEmail(campos[4]);
                usuarioDireccion.Usuario.setPassword(campos[5]);
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                usuarioDireccion.Usuario.setFechaNacimiento(formato.parse(campos[6]));
                usuarioDireccion.Usuario.setSexo(campos[7]);
                usuarioDireccion.Usuario.setTelefono(campos[8]);
                usuarioDireccion.Usuario.setCelular(campos[9]);
                usuarioDireccion.Usuario.setCURP(campos[10]);
                usuarioDireccion.Usuario.setImagen(null);
                usuarioDireccion.Usuario.setEstatus(Integer.parseInt(campos[11]));
                usuarioDireccion.Usuario.rol = new Rol();
                usuarioDireccion.Usuario.rol.setIdRol(Integer.parseInt(campos[12]));
                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Direccion.setCalle(campos[13]);
                usuarioDireccion.Direccion.setNumeroInterior(campos[14]);
                usuarioDireccion.Direccion.setNumeroExterior(campos[15]);
                usuarioDireccion.Direccion.Colonia = new Colonia();
                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(campos[16]));
                usuarios.add(usuarioDireccion);
            }

        } catch (Exception e) {
            usuarios = null;
        }
        return usuarios;
    }

    private List<UsuarioDireccion> LecturaEXCEL(File archivo) {
        List<UsuarioDireccion> usuarios = new ArrayList();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setNombre(row.getCell(0).toString());
                    usuarioDireccion.Usuario.setApellidoPaterno(row.getCell(1).toString());
                    usuarioDireccion.Usuario.setApellidoMaterno(row.getCell(2).toString());
                    usuarioDireccion.Usuario.setUserName(row.getCell(3).toString());
                    usuarioDireccion.Usuario.setEmail(row.getCell(4).toString());
                    usuarioDireccion.Usuario.setPassword(row.getCell(5).toString());
                    usuarioDireccion.Usuario.setFechaNacimiento(row.getCell(6).getDateCellValue());
                    usuarioDireccion.Usuario.setSexo(row.getCell(7).toString());
                    //Se convierte un string                    
                    DataFormatter dataFormatter = new DataFormatter();
                    usuarioDireccion.Usuario.setTelefono(dataFormatter.formatCellValue(row.getCell(8)));
                    //Se convierte a un string                 
                    usuarioDireccion.Usuario.setCelular(dataFormatter.formatCellValue(row.getCell(9)));
                    usuarioDireccion.Usuario.setCURP(row.getCell(10).toString());
                    usuarioDireccion.Usuario.setEstatus((int) row.getCell(11).getNumericCellValue());
                    usuarioDireccion.Usuario.rol = new Rol();
                    usuarioDireccion.Usuario.rol.setIdRol((int) row.getCell(12).getNumericCellValue());
                    usuarioDireccion.Direccion = new Direccion();
                    usuarioDireccion.Direccion.setCalle(row.getCell(13).toString());
                    usuarioDireccion.Direccion.setNumeroInterior(row.getCell(14).toString());
                    usuarioDireccion.Direccion.setNumeroExterior(row.getCell(15).toString());
                    usuarioDireccion.Direccion.Colonia = new Colonia();
                    usuarioDireccion.Direccion.Colonia.setIdColonia((int) row.getCell(16).getNumericCellValue());
                    usuarios.add(usuarioDireccion);
                }

            }

        } catch (Exception e) {
            usuarios = null;
        }
        return usuarios;
    }

    private List<ResultFile> ValidaArchivos(List<UsuarioDireccion> usuarios) {
        List<ResultFile> listaErrores = new ArrayList<>();
        if (usuarios == null) {
            listaErrores.add(new ResultFile(0, "Objeto null", "Posible error a la hora de leer archivo"));
        } else if (usuarios.isEmpty()) {
            listaErrores.add(new ResultFile(0, "Objeto vacio", "Posible error a la hora de leer archivo"));
        } else {
            int fila = 1;
            for (UsuarioDireccion usuarioDireccion : usuarios) {
                if ((usuarioDireccion.Usuario.getNombre() == null) || (usuarioDireccion.Usuario.getNombre().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "Nombre vacio o null", "El campo nombre es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getApellidoPaterno() == null) || (usuarioDireccion.Usuario.getApellidoPaterno().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "Apellido Paterno vacio o null", "El campo apellido paterno es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getApellidoMaterno() == null) || (usuarioDireccion.Usuario.getApellidoMaterno().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "Apellido Materno vacio o null", "El campo apellido materno es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getUserName() == null) || (usuarioDireccion.Usuario.getUserName().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "UseName vacio o null", "El campo username es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getEmail() == null) || (usuarioDireccion.Usuario.getEmail().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "Email vacio o null", "El campo email es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getPassword() == null) || (usuarioDireccion.Usuario.getPassword().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "Password vacio o null", "El campo password es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getFechaNacimiento() == null)) {
                    listaErrores.add(new ResultFile(fila, "Fecha de Nacimiento null", "El campo fecha nacimiento es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getSexo() == null) || (usuarioDireccion.Usuario.getSexo().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "Sexo vacio o null", "El campo sexo es obligatorio"));
                }
                if ((usuarioDireccion.Usuario.getTelefono() == null) || (usuarioDireccion.Usuario.getTelefono().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "Telefono vacio o null", "El campo telefono es obligatorio"));
                }
                /*
                Intetar de resolver el casteo telefono
                Pattern pattern = Pattern.compile("^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
                Matcher matcher = pattern.matcher(usuarioDireccion.Usuario.getTelefono());
                if (!matcher.find()) {
                    listaErrores.add(new ResultFile(fila, "Telefono incorrecto" + usuarioDireccion.Usuario.getTelefono()+"k" , "El campo telefono tiene que ser de tipo texto"));
                }
                 */
                if ((usuarioDireccion.Usuario.getEstatus() > 1) || (usuarioDireccion.Usuario.getEstatus() < 0)) {
                    listaErrores.add(new ResultFile(fila, "Estatus incorrecto", "El campo estatus solo puede ser 0 o 1"));
                }
                if ((usuarioDireccion.Usuario.rol.getIdRol() < 1) || (usuarioDireccion.Usuario.rol.getIdRol() > 3)) {
                    listaErrores.add(new ResultFile(fila, "IdRol incorrecto " + usuarioDireccion.Usuario.rol.getIdRol(), "El campo IdRol no es el id de los roles existentes"));
                }
                if ((usuarioDireccion.Direccion.getCalle() == null) || (usuarioDireccion.Direccion.getCalle().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "La calle es vacio o null", "El campo calle es obligatorio"));
                }
                if ((usuarioDireccion.Direccion.getNumeroExterior() == null) || (usuarioDireccion.Direccion.getNumeroExterior().isEmpty())) {
                    listaErrores.add(new ResultFile(fila, "Numero Exterior vacio o null", "El campo Numero Exterior es obligatorio"));
                }
                fila++;
            }
        }
        return listaErrores;
    }

    @GetMapping("CargaMasiva/Procesar")
    public String ProcesaCargaMasiva(HttpSession session) {
        String tipoArchivo = session.getAttribute("urlFile").toString().split("\\.")[1];
        List<UsuarioDireccion> usuarios = null;
        if (tipoArchivo.equals("txt")) {
            usuarios = this.LecturaArchivoTXT(new File(session.getAttribute("urlFile").toString()));
        } else {
            usuarios = this.LecturaEXCEL(new File(session.getAttribute("urlFile").toString()));
        }
        for (UsuarioDireccion usuarioDireccion : usuarios) {
            this.UsuarioDAOImplementacion.UsuarioAdd(usuarioDireccion);
        }
        session.removeAttribute("urlFile");
        return "redirect:/Usuario";
    }
}
