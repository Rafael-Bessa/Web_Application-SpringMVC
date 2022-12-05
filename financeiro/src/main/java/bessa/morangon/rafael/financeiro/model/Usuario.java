package bessa.morangon.rafael.financeiro.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(unique = true, nullable = false, name = "email")
    private String username;
    @Column(nullable = false, name = "senha")
    private String password;

    @Column(nullable = true, name = "nome")
    private String nome;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Importacao> importacoes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
