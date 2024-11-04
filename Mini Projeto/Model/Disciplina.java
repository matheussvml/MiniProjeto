package Model;

public class Disciplina {
    private String id;
    private String curso;
    private String nome;

    public Disciplina(String id, String curso, String nome) {
        this.id = id;
        this.curso = curso;
        this.nome = nome;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public String toString() {
        return "Disciplina [id=" + id + ", curso=" + curso + ", nome=" + nome + "]";
    }

    
    
}
