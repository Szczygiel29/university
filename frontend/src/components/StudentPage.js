import React, {useState, useEffect} from "react";
import Loader from "./Loader";
import './StudentPage.css';

function StudentPage() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await fetch('http://localhost:8080/student');
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const responseData = await response.json();
            setData(responseData);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching data:', error);
            setLoading(false);
        }
    };

    if (loading) {
        return <Loader/>
    }

    return (
        <div>
            <table className="student-table">
                <thead>
                <tr>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Nr Indeksu</th>
                    <th>Oceny</th>
                    <th>ID</th>
                </tr>
                </thead>
                {data.map((student, index) => (
                    <tbody key={index}>
                    <tr>
                        <td>{student.imie ? student.imie : 'Brak'}</td>
                        <td>{student.nazwisko ? student.nazwisko : 'Brak'}</td>
                        <td>{student.nrIndeksu ? student.nrIndeksu : 'Brak'}</td>
                        <td>
                            <ul>
                                {student.oceny && student.oceny.length > 0 ? (
                                    student.oceny.map((ocena) => <li key={ocena.id}>Ocena: {ocena.ocena}</li>)
                                ) : (
                                    <li>Brak</li>
                                )}
                            </ul>
                        </td>
                        <td>{student.id ? student.id : 'Brak'}</td>
                    </tr>
                    </tbody>
                ))}
            </table>
        </div>
    )
}

export default StudentPage;