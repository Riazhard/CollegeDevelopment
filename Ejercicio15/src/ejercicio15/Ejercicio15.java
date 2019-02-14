
package ejercicio15;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Miembro {
    int padre;
    int raizRivales;
    int sizeBelow;
    
    Miembro(int i){
        padre = i;
        raizRivales = -1; // -1 = no tiene rivales
        sizeBelow = 1;
    }

    public int getRaizAliados() {
        return padre;
    }

    public void setRaizAliados(int raizAliados) {
        this.padre = raizAliados;
    }

    public int getRaizRivales() {
        return raizRivales;
    }

    public void setRaizRivales(int raizRivales) {
        this.raizRivales = raizRivales;
    }
    
}

class Bosque {
    Miembro[] estudiantes;
    //int componentes;
    
    Bosque(int tam){
       estudiantes = new Miembro[tam];
       for(int i = 0;i<tam;i++){
           estudiantes[i] = new Miembro(i);
       }
    }
    
    public void formarAlianza(int team1, int team2){
        int raizteam1 = findRaiz(team1);
        int raizteam2 = findRaiz(team2);
        if(raizteam1 != raizteam2){
            if(estudiantes[raizteam1].raizRivales == raizteam2 && estudiantes[raizteam2].raizRivales == raizteam1){
                System.out.println("Error");
            }else{
                int raizRival1 = estudiantes[raizteam1].raizRivales;
                int raizRival2 = estudiantes[raizteam2].raizRivales;
                if(estudiantes[raizteam1].sizeBelow <= estudiantes[raizteam2].sizeBelow){
                    estudiantes[raizteam1].padre = raizteam2;
                    estudiantes[raizteam2].sizeBelow += estudiantes[raizteam1].sizeBelow;
                    
                    if(raizRival1 != -1){
                        if(raizRival2 != -1){
                            
                            if(estudiantes[raizRival1].sizeBelow <= estudiantes[raizRival2].sizeBelow){
                                estudiantes[raizRival1].padre = raizRival2;
                                estudiantes[raizRival2].sizeBelow += estudiantes[raizRival1].sizeBelow;
                                estudiantes[raizteam2].raizRivales = raizRival2;
                                estudiantes[raizRival2].raizRivales  = raizteam2;
                            }else{
                                estudiantes[raizRival2].padre = raizRival1;
                                estudiantes[raizRival1].sizeBelow += estudiantes[raizRival2].sizeBelow;
                                estudiantes[raizteam2].raizRivales = raizRival1;
                                estudiantes[raizRival1].raizRivales = raizteam2;
                            }
                            
                        }else{
                            estudiantes[raizteam2].raizRivales = raizRival1;
                            estudiantes[raizRival1].raizRivales = raizteam2;
                        }
                    }
                    
                }else{
                    estudiantes[raizteam2].padre = raizteam1;
                    estudiantes[raizteam1].sizeBelow += estudiantes[raizteam2].sizeBelow; 
                    
                    if(raizRival1 != -1){
                        if(raizRival2 != -1){
                            
                            if(estudiantes[raizRival1].sizeBelow <= estudiantes[raizRival2].sizeBelow){
                                estudiantes[raizRival1].padre = raizRival2;
                                estudiantes[raizRival2].sizeBelow += estudiantes[raizRival1].sizeBelow;
                                estudiantes[raizteam1].raizRivales = raizRival2;
                                estudiantes[raizRival2].raizRivales = raizteam1;
                            }else{
                                estudiantes[raizRival2].padre = raizRival1;
                                estudiantes[raizRival1].sizeBelow += estudiantes[raizRival2].sizeBelow;
                                estudiantes[raizteam1].raizRivales = raizRival1;
                                estudiantes[raizRival1].raizRivales = raizteam1;
                            }
                            
                        }
                        
                    }else if(raizRival2 != -1){
                        estudiantes[raizteam1].raizRivales = raizRival2;
                        estudiantes[raizRival2].raizRivales = raizteam1;
                    }             
                }
            }
        }
    }
    
    public void formarRival(int team1, int team2){
        int raizteam1 = findRaiz(team1);
        int raizteam2 = findRaiz(team2);
        //System.out.println(team1+": "+raizteam1+"; "+team2+": "+raizteam2);
        if(raizteam1 != raizteam2){
            int raizRival1 = estudiantes[raizteam1].raizRivales;
            //System.out.println("Rival de "+raizteam1+": "+raizRival1);
            int raizRival2 = estudiantes[raizteam2].raizRivales;
            //System.out.println("Rival de "+raizteam2+": "+raizRival2);
            if(raizRival1 != raizteam2 && raizRival2 != raizteam1){
                if(raizRival1 != -1){
                    if(raizRival2 != -1){
                        if(estudiantes[raizRival1].sizeBelow <= estudiantes[raizteam2].sizeBelow){
                            estudiantes[raizRival1].padre = raizteam2;
                            estudiantes[raizteam2].sizeBelow += estudiantes[raizRival1].sizeBelow;

                            if(estudiantes[raizRival2].sizeBelow <= estudiantes[raizteam1].sizeBelow){
                                estudiantes[raizRival2].padre = raizteam1;
                                estudiantes[raizteam1].sizeBelow += estudiantes[raizRival2].sizeBelow;
                                estudiantes[raizteam2].raizRivales = raizteam1;
                                estudiantes[raizteam1].raizRivales = raizteam2;
                            }else{
                                estudiantes[raizteam1].padre = raizRival2;
                                estudiantes[raizRival2].sizeBelow += estudiantes[raizteam1].sizeBelow;
                                estudiantes[raizteam2].raizRivales = raizRival2;
                                estudiantes[raizRival2].raizRivales = raizteam2;
                            }

                        }else{
                            estudiantes[raizteam2].padre = raizRival1;
                            estudiantes[raizRival1].sizeBelow += estudiantes[raizteam2].sizeBelow;

                            if(estudiantes[raizRival2].sizeBelow <= estudiantes[raizteam1].sizeBelow){
                                estudiantes[raizRival2].padre = raizteam1;
                                estudiantes[raizteam1].sizeBelow += estudiantes[raizRival2].sizeBelow;
                                estudiantes[raizRival1].raizRivales = raizteam1;
                                estudiantes[raizteam1].raizRivales = raizRival1;
                            }else{
                                estudiantes[raizteam1].padre = raizRival2;
                                estudiantes[raizRival2].sizeBelow += estudiantes[raizteam1].sizeBelow;
                                estudiantes[raizRival1].raizRivales = raizRival2;
                                estudiantes[raizRival2].raizRivales = raizRival1;
                            }

                        }

                    }else{
                        formarAlianza(raizRival1, raizteam2);
                    }
                }else if(raizRival2 != -1){
                    formarAlianza(raizRival2, raizteam1);
                }else{
                    estudiantes[raizteam1].raizRivales = raizteam2;
                    estudiantes[raizteam2].raizRivales = raizteam1; 
                }
        }
        }else{
            System.out.println("Error");
        }      
    }
    
    public void alianza(int a,int b){
        //System.out.println("Alianza");
        //System.out.println("Raiz de "+a+": "+findRaiz(a)+" Raiz de "+b+":"+findRaiz(b));
        if(findRaiz(a) == findRaiz(b)){
            System.out.println("SI");
        }else{
            System.out.println("NO");
        }
    }
    
    public void rival(int a, int b){
        int raizA = findRaiz(a);
        int raizB = findRaiz(b);
        //System.out.println("Raiz de "+a+":"+raizA+" y rival: "+ estudiantes[raizA].raizRivales);
        //System.out.println("Raiz de "+b+":"+raizB+" y rival: "+ estudiantes[raizB].raizRivales);
        if(estudiantes[findRaiz(raizB)].raizRivales == raizA &&
                estudiantes[findRaiz(raizA)].raizRivales == raizB){
            System.out.println("SI");
        }else{
            System.out.println("NO");
        }
    }
    
    private int findRaiz(int a){
        if(estudiantes[a].padre == a){
            return a;
        }
        return estudiantes[a].padre = findRaiz(estudiantes[a].padre);
    }
    
}

public class Ejercicio15 {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader (System.in));
        StringTokenizer in;
        Bosque unal = new Bosque(Integer.parseInt(br.readLine()));
        
        String aux;
        
        while(br.ready()){
            in =  new StringTokenizer(br.readLine());
            aux = in.nextToken();
            if(aux.equals("FormarAlianza")){
                unal.formarAlianza(Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken()));
            }else if(aux.equals("Alianza")){
                unal.alianza(Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken()));
            }else if(aux.equals("FormarRival")){
                unal.formarRival(Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken()));
            }else{
                unal.rival(Integer.parseInt(in.nextToken()), Integer.parseInt(in.nextToken()));
            }
            
        }
       
    }
    
}
