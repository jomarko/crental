<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>
<table>
    <tr>
        <th><s:label for="e1" name="employee.name"/></th>
        <td><s:text id="e1" name="employee.name"/></td>
    </tr>
    <tr>
        <th><s:label for="e2" name="employee.password"/></th>
        <td><s:text id="e2" name="employee.password"/></td>
    </tr>
    <tr>
        <th><s:label for="e3" name="employee.accessRight"/></th>
        <td>
            <s:select id="e3" name="employee.accessRight"><s:options-enumeration enum="cz.muni.fi.pompe.crental.entity.AccessRight"/></s:select>
        </td>
    </tr>
</table>